package com.igrejacristahangar.cantina.modules.cliente.repository;

import com.igrejacristahangar.cantina.modules.cliente.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {

    Optional<Cliente> findClienteByCpf(String cpf);
}
