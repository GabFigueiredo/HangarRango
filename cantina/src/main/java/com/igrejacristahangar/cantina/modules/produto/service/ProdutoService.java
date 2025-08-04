package com.igrejacristahangar.cantina.modules.produto.service;

import java.util.UUID;

import com.igrejacristahangar.cantina.exceptions.InactiveProductException;
import com.igrejacristahangar.cantina.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.igrejacristahangar.cantina.modules.produto.dto.ProdutoRequestDTO;
import com.igrejacristahangar.cantina.modules.produto.dto.ProdutoResponseDTO;
import com.igrejacristahangar.cantina.modules.produto.mapper.ProdutoMapper;
import com.igrejacristahangar.cantina.modules.produto.model.Produto;
import com.igrejacristahangar.cantina.modules.produto.repository.ProdutoRepository;

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
    public Produto encontrarProdutoPeloSeuID(UUID requestId) {
        return produtoRepository.findById(requestId)
            .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado." + requestId, "id"));
    }

    /**
     * Busca, verifica se o status do produto está ativo, retorna o Produto
     *
     * @param produto produto
     * @return Produto
     * @throws ResourceNotFoundException se o produto não existir
     * @throws InactiveProductException se o produto estiver inativo
     */
    public boolean verificarStatusDoPedido(Produto produto) {

        if (produto.isStatus()) {
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
        Produto novoProduto = Produto.builder()
            .nome(produtoRequest.getNome())
            .preco(produtoRequest.getPreco())
            .status(false)
            .descricao(produtoRequest.getDescricao())
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
    public ProdutoResponseDTO alterarStatusDoProduto(Produto produto, boolean novoStatus) {

        produto.setStatus(novoStatus);

        produtoRepository.save(produto);

        return produtoMapper.ProductToResponse(produto);
    }

}
