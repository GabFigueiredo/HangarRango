package com.igrejacristahangar.cantina.modules.pedido.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.igrejacristahangar.cantina.modules.pedido.enums.STATUS;
import org.springframework.data.jpa.repository.JpaRepository;

import com.igrejacristahangar.cantina.modules.pedido.model.Pedido;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PedidoRepository extends JpaRepository<Pedido, UUID>, JpaSpecificationExecutor<Pedido> {
    @Query("SELECT MAX(p.numeroPedido) from Pedido p")
    Integer findMaxNumeroPedido();

    @Query("SELECT SUM(preco) AS total_preco FROM Pedido WHERE statusPagamento = 'EFETUADO'")
    BigDecimal CountSuccesfullTotalOrdersPrice();

    @Query("SELECT SUM(p.preco) FROM Pedido p " +
            "WHERE p.statusPagamento = 'EFETUADO' " +
            "AND p.createdAt >= :startOfDay AND p.createdAt < :endOfDay")
    BigDecimal CountTodaySuccesfullTotalOrdersPrice(@Param("startOfDay") LocalDateTime startOfDay,
                                                    @Param("endOfDay") LocalDateTime endOfDay);

    List<Pedido> findAllByStatus(STATUS status);
}
