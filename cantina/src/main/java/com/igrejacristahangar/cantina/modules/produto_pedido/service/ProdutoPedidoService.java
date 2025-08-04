package com.igrejacristahangar.cantina.modules.produto_pedido.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.igrejacristahangar.cantina.modules.produto.repository.ProdutoRepository;
import com.igrejacristahangar.cantina.modules.produto_pedido.dto.ProdutoPedidoRequestDTO;
import com.igrejacristahangar.cantina.modules.produto_pedido.model.Produto_Pedido;
import com.igrejacristahangar.cantina.modules.produto_pedido.repository.ProdutoPedidoRepository;
import org.springframework.stereotype.Service;

@Service
public class ProdutoPedidoService {
    
    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    ProdutoPedidoRepository produtoPedidoRepository;


    /**
     * Cria e salva os registros de associação entre produtos e um pedido com base nos dados recebidos.
     * <p>
     * Para cada produto incluído no pedido, verifica se ele existe no banco de dados. 
     * Se existir, calcula o preço total (quantidade × preço unitário) e salva no repositório.
     * Caso contrário, lança uma exceção.
     *
     * @param pedidoRequest objeto contendo o pedido, 
     * @throws RuntimeException se algum dos produtos informados não existir no banco de dados
     */
    public Produto_Pedido criarProdutoPedido(ProdutoPedidoRequestDTO request) {

        Produto_Pedido novoProduto_Pedido = Produto_Pedido
            .builder()
            .produto(request.getProduto())
            .pedido(request.getPedido())
            .quantidade(request.getQuantidade())
            .preco(request.getQuantidade() * request.getProduto().getPreco())
            .build();

        
        return produtoPedidoRepository.save(novoProduto_Pedido);
    }
}
