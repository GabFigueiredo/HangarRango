package com.igrejacristahangar.cantina.modules.pedido.repository;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.igrejacristahangar.cantina.modules.pedido.model.Pedido;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PedidoRepository extends JpaRepository<Pedido, UUID>, JpaSpecificationExecutor<Pedido> {
    @Query("SELECT MAX(p.numeroPedido) from Pedido p")
    Integer findMaxNumeroPedido();

    @Query("SELECT SUM(preco) AS total_preco FROM Pedido WHERE statusPagamento = 'EFETUADO'")
    Double CountSuccesfullTotalOrdersPrice();

    @Query("SELECT SUM(p.preco) FROM Pedido p " +
            "WHERE p.statusPagamento = 'EFETUADO' " +
            "AND p.createdAt >= :startOfDay AND p.createdAt < :endOfDay")
    Double CountTodaySuccesfullTotalOrdersPrice(@Param("startOfDay") LocalDateTime startOfDay,
                                           @Param("endOfDay") LocalDateTime endOfDay);
}
