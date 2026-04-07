package com.igrejacristahangar.cantina.application.service;

import java.util.List;
import java.util.UUID;

import com.igrejacristahangar.cantina.exceptions.InactiveProductException;
import com.igrejacristahangar.cantina.exceptions.ResourceNotFoundException;
import com.igrejacristahangar.cantina.enterprise.order.dto.DetalhesProdutoDTO;
import com.igrejacristahangar.cantina.enterprise.order.dto.PedidoRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.igrejacristahangar.cantina.infra.controller.product.dto.ProdutoRequestDTO;
import com.igrejacristahangar.cantina.infra.controller.product.dto.ProdutoResponseDTO;
import com.igrejacristahangar.cantina.infra.controller.product.mapper.ProdutoMapper;
import com.igrejacristahangar.cantina.enterprise.product.Product;
import com.igrejacristahangar.cantina.enterprise.repositories.ProdutoRepository;

@Service
public class ProdutoService {
    
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoMapper produtoMapper;

    /**
     * Busca um produto pelo seu id
     *
     * @param requestId ID do produto (UUID)
     * @return ProdutoResponseDTO - DTO de response do produto
     * @throws ResourceNotFoundException Se o produto não for encontrado
     */
    public Product encontrarProdutoPeloSeuID(UUID requestId) {
        return produtoRepository.findById(requestId)
            .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado." + requestId, "id"));
    }

    public List<ProdutoResponseDTO> buscarTodosProdutos() {
        return produtoMapper.ProductListToResponseList(produtoRepository.findAll());
    }

    /**
     * Busca, verifica se o status do produto está ativo, retorna o Produto
     *
     * @param produto produto
     * @return Produto
     * @throws ResourceNotFoundException se o produto não existir
     * @throws InactiveProductException se o produto estiver inativo
     */
    public boolean verificarStatusDoPedido(Product produto) {

        if (Boolean.TRUE.equals(produto.getStatus())) {
            return true;
        }

        throw new InactiveProductException("Produto inativo: " + produto.getId(), "id");
    }

    /**
     * Cria um produto
     *
     * @param produtoRequest
     * @return ProdutoResponseDTO
     */
    public ProdutoResponseDTO criarProduto(ProdutoRequestDTO produtoRequest) {
        Product novoProduto = Product.builder()
            .nome(produtoRequest.getNome())
            .preco(produtoRequest.getPreco())
            .descricao(produtoRequest.getDescricao())
                .quantidade(produtoRequest.getQuantidade())
                .status(Boolean.TRUE.equals(produtoRequest.getStatus()))
            .build();

        var produtoSalvo = produtoRepository.save(novoProduto);

        return produtoMapper.ProductToResponse(produtoSalvo);
    }

    /**
     * Altera o status de um produto.
     *
     * @param produto Produto para ser alterado
     * @param novoStatus novo status
     * @return ProdutoResponseDTO
     */
    public ProdutoResponseDTO alterarStatusDoProduto(Product produto, boolean novoStatus) {

        produto.setStatus(novoStatus);

        produtoRepository.save(produto);

        return produtoMapper.ProductToResponse(produto);
    }

    /**
     * Busca os produtos correspondentes aos IDs enviados no PedidoRequestDTO
     * e garante que todos estejam ativos.
     *
     * <p>O método realiza os seguintes passos:</p>
     * <ol>
     *     <li>Extrai os IDs de produto da lista de detalhes do pedido.</li>
     *     <li>Busca todos os produtos de uma vez usando o repositório.</li>
     *     <li>Verifica se cada produto está ativo.</li>
     *     <li>Lança uma InactiveProductException caso algum produto esteja inativo.</li>
     * </ol>
     *
     * @param requestDTO DTO contendo os detalhes do pedido, incluindo os IDs dos produtos.
     * @return Lista de produtos ativos correspondentes aos IDs fornecidos.
     * @throws InactiveProductException se algum produto estiver inativo.
     */
    public List<Product> buscarProdutosAtivos(PedidoRequestDTO requestDTO) {
        List<UUID> produtoIds = requestDTO.getItens().stream()
                .map(DetalhesProdutoDTO::getProdutoId)
                .toList();

        // Busca todos os produtos de uma vez só
        List<Product> produtos = produtoRepository.findAllById(produtoIds);

        // Verifica se todos os produtos existem e estão ativos
        for (Product produto : produtos) {
            if (Boolean.FALSE.equals(produto.getStatus())) {
                throw new InactiveProductException("Produto inativo", produto.getNome());
            }
        }

        return produtos;
    }

    public List<ProdutoResponseDTO> buscarTodosProdutosDisponiveis() {
        return produtoMapper.ProductListToResponseList(produtoRepository.findAllAvailableProducts());
    }

}
