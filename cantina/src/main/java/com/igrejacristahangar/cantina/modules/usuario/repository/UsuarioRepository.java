package com.igrejacristahangar.cantina.modules.usuario.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.igrejacristahangar.cantina.modules.usuario.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    
}
