package com.igrejacristahangar.cantina.modules.produto.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ProdutoResponseDTO {
    private UUID id;
    private String nome;
    private BigDecimal preco;
    private String descricao;
    private Integer quantidade;
    private boolean status;
}
