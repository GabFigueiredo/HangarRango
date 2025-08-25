package com.igrejacristahangar.cantina.modules.produto.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.igrejacristahangar.cantina.modules.produto.model.Produto;
import org.springframework.data.jpa.repository.Query;

public interface ProdutoRepository extends JpaRepository<Produto, UUID> {

    @Query("SELECT p FROM Produto p WHERE p.quantidade > 0 AND p.status = true")
    List<Produto> findAllAvailableProducts();

}
