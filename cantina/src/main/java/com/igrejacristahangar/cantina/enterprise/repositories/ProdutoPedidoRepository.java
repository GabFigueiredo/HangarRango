package com.igrejacristahangar.cantina.enterprise.repositories;

import java.util.UUID;

import com.igrejacristahangar.cantina.enterprise.model.ProdutoPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoPedidoRepository extends JpaRepository<ProdutoPedido, UUID> {

}
