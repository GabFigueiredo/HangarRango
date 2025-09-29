package com.igrejacristahangar.cantina.modules.pedido.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.igrejacristahangar.cantina.modules.abacatepay.dto.CreatePixQrCodeDTO;
import com.igrejacristahangar.cantina.modules.abacatepay.dto.Metadata;
import com.igrejacristahangar.cantina.modules.abacatepay.dto.PixQrCodeResponseDTO;
import com.igrejacristahangar.cantina.modules.abacatepay.service.AbacateService;
import com.igrejacristahangar.cantina.modules.cliente.Cliente;
import com.igrejacristahangar.cantina.modules.cliente.dto.ClienteRequestDTO;
import com.igrejacristahangar.cantina.modules.cliente.service.ClienteService;
import com.igrejacristahangar.cantina.modules.pedido.dto.*;
import com.igrejacristahangar.cantina.modules.pedido.enums.FORMA_PAGAMENTO;
import com.igrejacristahangar.cantina.modules.pedido.enums.STATUS_PAGAMENTO;
import com.igrejacristahangar.cantina.modules.pedido.repository.PedidoSpec;
import com.igrejacristahangar.cantina.modules.produto.repository.ProdutoRepository;
import com.igrejacristahangar.cantina.modules.produto_pedido.model.ProdutoPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.igrejacristahangar.cantina.modules.pedido.enums.STATUS;
import com.igrejacristahangar.cantina.modules.pedido.mapper.PedidoMapper;
import com.igrejacristahangar.cantina.modules.pedido.model.Pedido;
import com.igrejacristahangar.cantina.modules.pedido.repository.PedidoRepository;
import com.igrejacristahangar.cantina.modules.produto.model.Produto;
import com.igrejacristahangar.cantina.modules.produto.service.ProdutoService;
import com.igrejacristahangar.cantina.modules.produto_pedido.service.ProdutoPedidoService;
import com.igrejacristahangar.cantina.exceptions.ResourceNotFoundException;
import com.igrejacristahangar.cantina.exceptions.InactiveProductException;
@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ProdutoPedidoService produtoPedidoService;

    @Autowired
    private PedidoMapper pedidoMapper;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private AbacateService abacateService;

    /**
     * Busca de pedidos por paginação.
     * @param pageable
     * @return Page<Pedido>
     */
    public Page<Pedido> buscarPedidosPorRange(Pageable pageable) {

        return pedidoRepository.findAll(pageable);
    }

    /**
     * Busca todos os pedidos ordenados por data.
     *
     * @return List<PedidoResponseDTO> com PedidoResponseDTO
     */
    public List<PedidoResponseDTO> buscarTodosOsPedidos() {
        return pedidoMapper.PedidoListToPedidoResponseDTOList(pedidoRepository.findAllOrderedByDate()
                .orElseThrow( () -> new ResourceNotFoundException("Pedidos não encontados", "id")));
    }

    public List<PedidoResponseDTO> buscarTodosPedidosPreparando() {
        return pedidoMapper.PedidoListToPedidoResponseDTOList(pedidoRepository.findAllByStatus(STATUS.PREPARANDO));
    }

    /**
     * Busca um pedido pelo seu ID.
     * @param id UUID do pedido
     * @return Pedido
     */
    public Pedido buscarPedidoPorID(UUID id) {
        return pedidoRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("ID não encontrado", "id"));
    }

    /**
     * Cria um novo pedido na cantina com os produtos informados no PedidoRequestDTO.
     *
     * <p>O método realiza os seguintes passos:</p>
     * <ol>
     *     <li>Busca todos os produtos ativos correspondentes aos IDs informados no pedido.</li>
     *     <li>Cria um objeto Pedido com os dados do cliente, forma de pagamento, status,
     *         e número do pedido.</li>
     *     <li>Chama a função criarItensDoPedido para gerar a lista de itens
     *         (ProdutoPedido) associando cada produto à quantidade e preço correto.</li>
     *     <li>Associa os itens ao pedido e salva tudo no banco em uma única operação
     *         (graças ao {@code CascadeType.ALL}).</li>
     *     <li>Converte o pedido salvo para {@link PedidoResponseDTO}.</li>
     *     <li>Envia uma notificação via WebSocket para o endpoint "/cantina/preparacao".</li>
     * </ol>
     *
     * @param requestDTO DTO contendo os dados do pedido, incluindo nome do cliente,
     *                   forma de pagamento, status do pagamento e lista de produtos.
     * @return PedidoResponseDTO contendo os dados do pedido recém-criado.
     * @throws InactiveProductException se algum produto informado estiver inativo.
     */
    public PedidoResponseDTO criarPedido(PedidoRequestDTO requestDTO) {
        // Busca produtos e verifica se estão ativos
        List<Produto> listaProdutos = produtoService.buscarProdutosAtivos(requestDTO);

        Cliente cliente;
        try {
            cliente = clienteService.findClienteByCpf(requestDTO.getCliente().getTaxId());
            System.out.println(cliente.toString());

        } catch (ResourceNotFoundException e) {
            cliente = clienteService.createClient(requestDTO.getCliente());
            System.out.println("Bateu aqui");
            System.out.println(cliente.toString());
        }

        // Criar o pedido (ainda sem itens)
        Pedido novoPedido = Pedido.builder()
                .cliente(cliente)
                .preco(requestDTO.getPreco())
                .formaPagamento(requestDTO.getFormaPagamento())
                .statusPagamento(requestDTO.getStatusPagamento())
                .status(STATUS.PREPARANDO)
                .numeroPedido(this.gerarCodigoDePedido())
                .build();

        switch (requestDTO.getFormaPagamento()) {
            case CARTAO, DINHEIRO, PIX -> novoPedido.setStatus(STATUS.PENDENTE);
            case MARCADO -> novoPedido.setStatus(STATUS.PREPARANDO);
        }

        // Criar itens do pedido e atualizar estoque no objeto
        List<ProdutoPedido> itensPedido = produtoPedidoService.criarItensDoPedido(
                listaProdutos,
                requestDTO.getItens(),
                novoPedido
        );

        // Atualiza o estoque dos produtos no banco
        for (ProdutoPedido pp: itensPedido) {
            produtoRepository.save(pp.getProduto());
        }

        // Associa os itens ao pedido
        novoPedido.setItens(itensPedido);

        // Salva o pedido junto com os itens
        Pedido pedidoSalvo = pedidoRepository.save(novoPedido);

        // Cria o pixQrCode, se a forma de pagamento for PIX.
        PixQrCodeResponseDTO abacateResponse = null;
        if (requestDTO.getFormaPagamento().equals(FORMA_PAGAMENTO.PIX)) {
            System.out.println("Bateu aqui");
            abacateResponse = criarPedidoPorPix(cliente, pedidoSalvo);
        }

        // Mapeia para DTO de resposta
        PedidoResponseDTO pedidoMapeado = pedidoMapper.PedidoToPedidoResponseDTO(pedidoSalvo);
        pedidoMapeado.setAbacateResponse(abacateResponse);

        // Envia notificação via WebSocket
        System.out.println(pedidoSalvo.getFormaPagamento());
        System.out.println(pedidoSalvo.getFormaPagamento().equals(FORMA_PAGAMENTO.MARCADO));
        if (pedidoSalvo.getFormaPagamento().equals(FORMA_PAGAMENTO.MARCADO)) {
            messagingTemplate.convertAndSend("/cantina/preparacao", pedidoMapeado);
        }

        return pedidoMapeado;
    }

    public PixQrCodeResponseDTO criarPedidoPorPix(Cliente cliente, Pedido pedido) {
        ClienteRequestDTO clienteRequest = ClienteRequestDTO.builder()
                .name(cliente.getName())
                .email(cliente.getEmail())
                .cellphone(cliente.getCellphone())
                .taxId(cliente.getCpf())
                .build();

        Metadata metadata = new Metadata(pedido.getId());

        CreatePixQrCodeDTO payload = CreatePixQrCodeDTO.builder()
                .amount(pedido.getPreco().unscaledValue().intValue())
                .customer(clienteRequest)
                .metadata(metadata)
                .build();

        return abacateService.criarPixQrCode(payload);
    }

    /**
     * Gera um novo codigo incrementando o maior código (MAX 1000).
     * <p>
     *
     * Se o maior código for igual a 1000, o próximo código será 1.
     * @return O código do pedido
     */
    public Integer gerarCodigoDePedido() {
        Integer ultimoNumero = pedidoRepository.findMaxNumeroPedido();
        Integer proximoNumero = (ultimoNumero != null) ? ultimoNumero + 1 : 1;

        if (proximoNumero > 1000) {
            proximoNumero = 1;
        }

        return proximoNumero;
    }

    /**
     * Altera o status de um pedido (PREPARANDO, CONCLUÍDO)
     * e o status de pagamento (CARTÃO, PIX, DINHEIRO, MARCADO)
     *
     * @param requestDTO ID do pedido, novo status de pedido e novo status de pagamento
     * @return PedidoResponseDTO
     */
    public PedidoResponseDTO alterarStatusDePagamento(StatusPagamentoDTO requestDTO) {
        Pedido pedido = this.buscarPedidoPorID(requestDTO.getId());

        pedido.setStatusPagamento(requestDTO.getStatus());

        switch (pedido.getFormaPagamento()) {
            case CARTAO, DINHEIRO -> pedido.setStatus(STATUS.PREPARANDO);
        }

        pedidoRepository.save(pedido);

        PedidoResponseDTO pedidoResponse = pedidoMapper.PedidoToPedidoResponseDTO(pedido);

        switch (pedido.getFormaPagamento()) {
            case CARTAO, DINHEIRO -> messagingTemplate.convertAndSend("/cantina/preparacao", pedidoResponse);
        }

        return pedidoResponse;
    }

    public PedidoResponseDTO alterarStatusDePedido(Pedido pedido, StatusRequestDTO requestDTO) {
        pedido.setStatus(requestDTO.getPedidoStatus());
        pedidoRepository.save(pedido);
        return pedidoMapper.PedidoToPedidoResponseDTO(pedido);
    }

    /**
     * Busca todos os pedidos que batem com o filtro, por paginação.
     * @param filtro FiltroPedidoDTO
     * @param pageable Paginação
     * @return Page<Pedido>
     */
    public Page<Pedido> buscarPedidosPorTodosOsFiltros(FiltroPedidoDTO filtro, Pageable pageable) {

        return pedidoRepository.findAll(PedidoSpec.filtrarPedidoPorTodosOsCampos(filtro), pageable);
    }

    /**
     * Calcula o Resumo de vendas: Total vendido e Total vendido HOJE
     *
     * @return PedidoResumo contém totalPreco, totalPrecoDeHoje
     */
    public PedidoResumo gerarResumoDeVendas() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);

        BigDecimal totalPreco =  pedidoRepository.CountSuccesfullTotalOrdersPrice();
        BigDecimal totalPrecoDeHoje = pedidoRepository.CountTodaySuccesfullTotalOrdersPrice(startOfDay, endOfDay);

        if (totalPreco == null) {
            totalPreco = BigDecimal.ZERO;
        }

        if (totalPrecoDeHoje == null) {
            totalPrecoDeHoje = BigDecimal.ZERO;
        }

        return PedidoResumo.builder()
                .totalPreco(totalPreco)
                .totalPrecoDeHoje(totalPrecoDeHoje)
                .build();
    }
}