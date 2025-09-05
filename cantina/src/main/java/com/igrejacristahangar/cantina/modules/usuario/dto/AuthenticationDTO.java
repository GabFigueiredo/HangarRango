package com.igrejacristahangar.cantina.modules.usuario.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthenticationDTO {
    @NotNull(message = "O email não pode estar vazio")
    private String email;

    @NotNull(message = "A senha não pode estar vazio")
    private String senha;
}
