package com.igrejacristahangar.cantina.enterprise.repositories;

import com.igrejacristahangar.cantina.enterprise.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {

    Optional<Cliente> findClienteByCpf(String cpf);
}
