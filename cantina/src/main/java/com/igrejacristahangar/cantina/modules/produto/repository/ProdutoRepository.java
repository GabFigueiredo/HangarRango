package com.igrejacristahangar.cantina.modules.produto.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.igrejacristahangar.cantina.modules.produto.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, UUID> {
    

}
