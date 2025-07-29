package com.igrejacristahangar.cantina.modules.pedido.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.igrejacristahangar.cantina.modules.pedido.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, UUID> {

}
