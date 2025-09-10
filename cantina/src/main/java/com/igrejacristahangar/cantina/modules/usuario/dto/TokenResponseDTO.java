package com.igrejacristahangar.cantina.modules.usuario.dto;

import com.igrejacristahangar.cantina.modules.usuario.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TokenResponseDTO {
    private UserResponseDTO usuario;
    private String token;
}
