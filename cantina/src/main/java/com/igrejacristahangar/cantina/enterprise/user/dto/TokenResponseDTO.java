package com.igrejacristahangar.cantina.enterprise.user.dto;

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
