package com.igrejacristahangar.cantina.modules.produto_pedido.repository;

import java.util.UUID;

import com.igrejacristahangar.cantina.modules.produto_pedido.model.ProdutoPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoPedidoRepository extends JpaRepository<ProdutoPedido, UUID> {

}
