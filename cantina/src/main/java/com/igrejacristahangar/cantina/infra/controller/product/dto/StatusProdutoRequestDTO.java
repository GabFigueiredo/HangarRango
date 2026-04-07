package com.igrejacristahangar.cantina.infra.controller.product.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class StatusProdutoRequestDTO {
    @NotNull(message = "O id não pode estar vazio")
    private UUID produtoId;

    @NotNull(message = "O status não pode estar vazio")
    private Boolean status;
}
