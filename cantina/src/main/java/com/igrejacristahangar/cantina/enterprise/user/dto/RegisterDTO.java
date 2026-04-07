package com.igrejacristahangar.cantina.enterprise.user.dto;

import com.igrejacristahangar.cantina.enterprise.user.enums.UserRole;
import lombok.Data;

@Data
public class RegisterDTO {
    private String nome;
    private String email;
    private String senha;
    private UserRole role;

}
