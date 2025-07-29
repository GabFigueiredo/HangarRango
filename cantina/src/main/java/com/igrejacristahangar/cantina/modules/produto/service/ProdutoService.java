package com.igrejacristahangar.cantina.modules.produto.service;

import java.util.UUID;

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
    
    public Produto encontrarProdutoPeloSeuID(UUID requestId) {
        Produto p = produtoRepository.findById(requestId)
            .orElseThrow(() -> new RuntimeException("Produto não se encontra no sistema"));

        return p;
    }

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


}
