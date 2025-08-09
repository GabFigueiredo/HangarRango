package com.igrejacristahangar.cantina.modules.pedido.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.igrejacristahangar.cantina.modules.pedido.dto.*;
import com.igrejacristahangar.cantina.modules.pedido.repository.PedidoSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.igrejacristahangar.cantina.modules.pedido.enums.STATUS;
import com.igrejacristahangar.cantina.modules.pedido.mapper.PedidoMapper;
import com.igrejacristahangar.cantina.modules.pedido.model.Pedido;
import com.igrejacristahangar.cantina.modules.pedido.repository.PedidoRepository;
import com.igrejacristahangar.cantina.modules.produto.model.Produto;
import com.igrejacristahangar.cantina.modules.produto.service.ProdutoService;
import com.igrejacristahangar.cantina.modules.produto_pedido.dto.ProdutoPedidoRequestDTO;
import com.igrejacristahangar.cantina.modules.produto_pedido.service.ProdutoPedidoService;
import com.igrejacristahangar.cantina.exceptions.ResourceNotFoundException;
import com.igrejacristahangar.cantina.exceptions.InactiveProductException;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ProdutoPedidoService ProdutoPedidoService;

    @Autowired
    private PedidoMapper pedidoMapper;

    /**
     * Busca de pedidos por paginaçãp
     * @param pageable
     * @return Page<Pedido>
     */
    public Page<Pedido> buscarPedidosPorRange(Pageable pageable) {

        return pedidoRepository.findAll(pageable);
    }

    public List<PedidoResponseDTO> buscarTodosOsPedidos() {
        return pedidoMapper.PedidoListToPedidoResponseDTOList(pedidoRepository.findAll());
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
     * Cria um novo pedido com base nos dados recebidos.
     * <p>
     * Esta função realiza as seguintes etapas:
     * <ul>
     *   <li>Verifica se os produtos desse pedido estão ativos e os coloca em uma lista.</li>
     *   <li>Constrói um novo objeto {@code Pedido} a partir dos dados do {@code PedidoRequestDTO}.</li>
     *   <li>Salva o pedido no banco de dados.</li>
     *   <li>Cria a associação entre o pedido e seus produtos através da {@code ProdutoPedidoService}.</li>
     *   <li>Retorna o {@code PedidoResponseDTO} correspondente ao pedido salvo.</li>
     * </ul>
     *
     * @param requestDTO Objeto contendo os dados do pedido a ser criado, incluindo cliente, forma de pagamento,
     *                   preço, status do pagamento e IDs dos produtos e a quantidade deles.
     * @return {@code PedidoResponseDTO} do pedido criado.
     * @throws ResourceNotFoundException se um dos produtos não for encontrado no sistema.
     * @throws InactiveProductException se algum produto estiver inativo
     * @throws MethodArgumentNotValidException se os dados do request estiverem inválidos
     */
    public PedidoResponseDTO criarPedido(PedidoRequestDTO requestDTO) {
        List<Produto> listaDeProdutos = new ArrayList<>();

        // Busca, depois verifica se todos os produtos estão ativos, adiciona na lista
        for (int i = 0; i < requestDTO.getDetalhesProdutos().size(); i++) {
            UUID produtoID = requestDTO.getDetalhesProdutos().get(i).getProdutoId();
            Produto produto = produtoService.encontrarProdutoPeloSeuID(produtoID);
            if (produtoService.verificarStatusDoPedido(produto)) {
                listaDeProdutos.add(produto);
            }
        }

        // Cria o novo pedido no banco
        Pedido novoPedido = Pedido.builder()
                .clienteNome(requestDTO.getClienteNome())
                .formaPagamento(requestDTO.getFormaPagamento())
                .preco(requestDTO.getPreco())
                .statusPagamento(requestDTO.getStatusPagamento())
                .status(STATUS.PREPARANDO)
                .numeroPedido(this.gerarCodigoDePedido())
                .build();

        var pedidoSalvo = pedidoRepository.save(novoPedido);

        // Criação dos ProdutoPedido com base na lista validada
        for (int i = 0; i < requestDTO.getDetalhesProdutos().size(); i++) {
            DetalhesProdutoDTO detalhes = requestDTO.getDetalhesProdutos().get(i);
            Produto produto = listaDeProdutos.get(i);

            ProdutoPedidoRequestDTO produtoPedido = ProdutoPedidoRequestDTO.builder()
                    .pedido(pedidoSalvo)
                    .produto(produto)
                    .quantidade(detalhes.getQuantidade())
                    .build();

            ProdutoPedidoService.criarProdutoPedido(produtoPedido);
        }

        var pedidoMapeado = pedidoMapper.PedidoToPedidoResponseDTO(pedidoSalvo);

        return pedidoMapper.PedidoToPedidoResponseDTO(pedidoSalvo);
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
     * @param pedido
     * @param requestDTO ID do pedido, novo status de pedido e novo status de pagamento
     * @return PedidoResponseDTO
     */
    public PedidoResponseDTO alterarStatusDePedidoEPagamento(Pedido pedido, StatusRequestDTO requestDTO) {
        pedido.setStatus(requestDTO.getPedidoStatus());
        pedido.setStatusPagamento(requestDTO.getStatusPagamento());

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

        Double totalPreco =  pedidoRepository.CountSuccesfullTotalOrdersPrice();
        Double totalPrecoDeHoje = pedidoRepository.CountTodaySuccesfullTotalOrdersPrice(startOfDay, endOfDay);

        if (totalPreco == null) {
            totalPreco = 0.0;
        }

        if (totalPrecoDeHoje == null) {
            totalPrecoDeHoje = 0.0;
        }

        PedidoResumo response = PedidoResumo.builder()
                .totalPreco(totalPreco)
                .totalPrecoDeHoje(totalPrecoDeHoje)
                .build();

        return response;
    }



}