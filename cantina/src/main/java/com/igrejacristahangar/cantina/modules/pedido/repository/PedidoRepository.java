package com.igrejacristahangar.cantina.modules.pedido.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.igrejacristahangar.cantina.modules.pedido.model.Pedido;
import org.springframework.data.jpa.repository.Query;

public interface PedidoRepository extends JpaRepository<Pedido, UUID> {
    @Query("SELECT MAX(p.numeroPedido) from Pedido p")
    Integer findMaxNumeroPedido();
}
