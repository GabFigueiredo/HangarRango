package com.igrejacristahangar.cantina.modules.pedido.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.igrejacristahangar.cantina.modules.pedido.dto.DetalhesProdutoDTO;
import com.igrejacristahangar.cantina.modules.pedido.dto.PedidoRequestDTO;
import com.igrejacristahangar.cantina.modules.pedido.dto.PedidoResponseDTO;
import com.igrejacristahangar.cantina.modules.pedido.enums.STATUS;
import com.igrejacristahangar.cantina.modules.pedido.enums.STATUS_PAGAMENTO;
import com.igrejacristahangar.cantina.modules.pedido.mapper.PedidoMapper;
import com.igrejacristahangar.cantina.modules.pedido.model.Pedido;
import com.igrejacristahangar.cantina.modules.pedido.repository.PedidoRepository;
import com.igrejacristahangar.cantina.modules.produto.model.Produto;
import com.igrejacristahangar.cantina.modules.produto.service.ProdutoService;
import com.igrejacristahangar.cantina.modules.produto_pedido.dto.ProdutoPedidoRequestDTO;
import com.igrejacristahangar.cantina.modules.produto_pedido.service.ProdutoPedidoService;

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

    public Page<Pedido> buscarPedidosPorRange(Pageable pageable) {
        var pedidos = pedidoRepository.findAll(pageable);

        return pedidos;
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
     *                   preço, status do pagamento e IDs dos produtos.
     * @return {@code PedidoResponseDTO} do pedido criado.
     * @throws RuntimeException caso ocorra algum erro durante a criação do pedido ou associação dos produtos.
     */
    public PedidoResponseDTO criarPedido(PedidoRequestDTO requestDTO) {
        List<Produto> listaDeProdutos = new ArrayList<>();

         // Busca e verifica se todos os produtos estão ativos, adiciona na lista
        for (DetalhesProdutoDTO detalhesDoProduto : requestDTO.getDetalhesProdutos()) {
            var produto = produtoService.encontrarProdutoPeloSeuID(detalhesDoProduto.getProdutoId());
            if (produto.isStatus()) {
                listaDeProdutos.add(produto);
            }
        }

        // Cria o novo pedido no banco
        Pedido novoPedido = Pedido.builder()
            .cliente_nome(requestDTO.getCliente_nome())
            .forma_pagamento(requestDTO.getForma_pagamento())
            .preco(requestDTO.getPreco())
            .status_pagamento(STATUS_PAGAMENTO.valueOf(requestDTO.getStatus_pagamento()))
            .status(STATUS.PREPARANDO)  
            .created_at(LocalDateTime.now())
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

        return pedidoMapper.PedidoToPedidoResponseDTO(pedidoSalvo);
    }
}
