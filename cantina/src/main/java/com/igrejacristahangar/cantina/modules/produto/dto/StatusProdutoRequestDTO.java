package com.igrejacristahangar.cantina.modules.produto.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class StatusProdutoRequestDTO {
    private UUID produtoId;
    private boolean status;
}
