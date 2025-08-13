package com.igrejacristahangar.cantina.modules.produto_pedido.service;

import com.igrejacristahangar.cantina.modules.pedido.dto.PedidoResponseDTO;
import com.igrejacristahangar.cantina.modules.pedido.dto.PreparacaoResponseDTO;
import com.igrejacristahangar.cantina.modules.pedido.dto.ProdutoPreparacaoDTO;
import org.springframework.beans.factory.annotation.Autowired;

import com.igrejacristahangar.cantina.modules.produto.repository.ProdutoRepository;
import com.igrejacristahangar.cantina.modules.produto_pedido.dto.ProdutoPedidoRequestDTO;
import com.igrejacristahangar.cantina.modules.produto_pedido.model.Produto_Pedido;
import com.igrejacristahangar.cantina.modules.produto_pedido.repository.ProdutoPedidoRepository;
import org.springframework.stereotype.Service;

import java.util.*;

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
     * @param request objeto contendo o pedido,
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

    public List<PreparacaoResponseDTO> buscarPedidosPreparandoComProdutos() {
        List<Produto_Pedido> lista = produtoPedidoRepository.findAllPreparingOrders();
        Map<UUID, PreparacaoResponseDTO> mapPedidos = new LinkedHashMap<>();

        for (Produto_Pedido produtoPedido : lista) {
            UUID pedidoId = produtoPedido.getPedido().getId();

            // Se ainda não existe DTO para esse pedido, cria e adiciona no map
            if (!mapPedidos.containsKey(pedidoId)) {
                PreparacaoResponseDTO prep = PreparacaoResponseDTO.builder()
                        .id(pedidoId)
                        .numeroPedido(produtoPedido.getPedido().getNumeroPedido())
                        .build();
                mapPedidos.put(pedidoId, prep);
            }

            // Adiciona o produto na lista de produtos do DTO
            ProdutoPreparacaoDTO produtoDto = ProdutoPreparacaoDTO.builder()
                    .nome(produtoPedido.getProduto().getNome())
                    .quantidade(produtoPedido.getQuantidade())
                    .build();

            mapPedidos.get(pedidoId).adicionarNaLista(produtoDto);
        }

        return new ArrayList<>(mapPedidos.values());
    }
}
