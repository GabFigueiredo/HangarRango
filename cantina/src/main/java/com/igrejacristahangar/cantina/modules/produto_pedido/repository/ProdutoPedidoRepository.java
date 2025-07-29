package com.igrejacristahangar.cantina.modules.produto_pedido.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.igrejacristahangar.cantina.modules.produto_pedido.model.Produto_Pedido;

public interface ProdutoPedidoRepository extends JpaRepository<Produto_Pedido, UUID> {
    
}
