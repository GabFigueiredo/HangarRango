package com.igrejacristahangar.cantina.modules.produto_pedido.repository;

import java.util.List;
import java.util.UUID;

import com.igrejacristahangar.cantina.modules.pedido.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import com.igrejacristahangar.cantina.modules.produto_pedido.model.Produto_Pedido;
import org.springframework.data.jpa.repository.Query;

public interface ProdutoPedidoRepository extends JpaRepository<Produto_Pedido, UUID> {

    @Query("SELECT pp FROM Produto_Pedido pp " +
            "JOIN FETCH pp.produto " +
            "JOIN FETCH pp.pedido p " +
            "WHERE p.status = 'PREPARANDO' " +
            "ORDER BY p.numeroPedido")
    List<Produto_Pedido> findAllPreparingOrders();

}
