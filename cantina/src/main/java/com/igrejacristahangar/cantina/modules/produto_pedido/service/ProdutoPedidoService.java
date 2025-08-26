package com.igrejacristahangar.cantina.modules.produto_pedido.service;

import com.igrejacristahangar.cantina.modules.pedido.dto.DetalhesProdutoDTO;
import com.igrejacristahangar.cantina.modules.pedido.model.Pedido;
import com.igrejacristahangar.cantina.modules.produto.model.Produto;
import com.igrejacristahangar.cantina.modules.produto_pedido.model.ProdutoPedido;
import org.springframework.beans.factory.annotation.Autowired;

import com.igrejacristahangar.cantina.modules.produto.repository.ProdutoRepository;
import com.igrejacristahangar.cantina.modules.produto_pedido.repository.ProdutoPedidoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class ProdutoPedidoService {
    
    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    ProdutoPedidoRepository produtoPedidoRepository;

    /**
     * Cria a lista de itens do pedido (ProdutoPedido) a partir dos produtos ativos
     * e dos detalhes enviados no pedido.
     *
     * @param listaProdutos Lista de produtos ativos correspondentes aos IDs do pedido
     * @param detalhesProdutos Lista de DetalhesProdutoDTO com quantidade por produto
     * @param pedido Pedido que será associado aos itens
     * @return Lista de ProdutoPedido pronta para persistência
     */
    public List<ProdutoPedido> criarItensDoPedido(
            List<Produto> listaProdutos,
            List<DetalhesProdutoDTO> detalhesProdutos,
            Pedido pedido
    ) {
        List<ProdutoPedido> itensPedido = new ArrayList<>();

        for (int i = 0; i < detalhesProdutos.size(); i++) {
            DetalhesProdutoDTO detalhes = detalhesProdutos.get(i);
            Produto produto = listaProdutos.get(i);

            // Verifica se há estoque suficiente
            if (detalhes.getQuantidade() > produto.getQuantidade()) {
                throw new IllegalArgumentException(
                        "Quantidade pedida do produto " + produto.getNome() +
                                " excede o estoque disponível."
                );
            }

            // Diminui a quantidade no estoque
            produto.setQuantidade(produto.getQuantidade() - detalhes.getQuantidade());

            ProdutoPedido item = ProdutoPedido.builder()
                    .pedido(pedido)
                    .produto(produto)
                    .quantidade(detalhes.getQuantidade())
                    .preco(produto.getPreco()
                            .multiply(BigDecimal.valueOf(detalhes.getQuantidade())))
                    .build();

            itensPedido.add(item);
        }

        return itensPedido;
    }

}
