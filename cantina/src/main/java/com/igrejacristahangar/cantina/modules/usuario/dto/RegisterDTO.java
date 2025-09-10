package com.igrejacristahangar.cantina.modules.usuario.dto;

import com.igrejacristahangar.cantina.modules.usuario.enums.UserRole;
import lombok.Data;

@Data
public class RegisterDTO {
    private String nome;
    private String email;
    private String senha;
    private UserRole role;

}
