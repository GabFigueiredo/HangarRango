package com.igrejacristahangar.cantina.modules.pedido.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProdutoQuantidadeDTO {
    @NotNull
    private UUID produtoId;

    @NotNull 
    private Integer quantidade;
}
