package com.igrejacristahangar.cantina.modules.usuario.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Usuario {
    
    @Id
    @GeneratedValue
    private UUID id;

    private String email;

    private String senha;

}
