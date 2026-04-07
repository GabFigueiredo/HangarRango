package com.igrejacristahangar.cantina.enterprise.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.igrejacristahangar.cantina.enterprise.product.Product;
import org.springframework.data.jpa.repository.Query;

public interface ProdutoRepository extends JpaRepository<Product, UUID> {

    @Query("SELECT p FROM Product p WHERE p.quantidade > 0 AND p.status = true")
    List<Product> findAllAvailableProducts();

}
