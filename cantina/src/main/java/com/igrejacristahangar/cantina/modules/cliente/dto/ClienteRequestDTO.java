package com.igrejacristahangar.cantina.modules.cliente.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteRequestDTO {
    @NotBlank(message = "O nome é obrigatório")
    private String name;

    @Email(message = "Email inválido")
    @NotNull(message = "O Email é obrigatório")
    private String email;

    @NotNull(message = "O telefone é obrigatório")
    private String cellphone;

    @NotNull(message = "O preço é obrigatório")
    @CPF(message = "CPF inválido")
    private String taxId;
}
