package com.igrejacristahangar.cantina.modules.usuario.repository;

import com.igrejacristahangar.cantina.modules.usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    UserDetails findByEmail(String email);
}
