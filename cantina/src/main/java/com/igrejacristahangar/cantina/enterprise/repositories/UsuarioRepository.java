package com.igrejacristahangar.cantina.enterprise.repositories;

import com.igrejacristahangar.cantina.enterprise.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<User, UUID> {

    UserDetails findByEmail(String email);
}
