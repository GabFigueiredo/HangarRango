package com.igrejacristahangar.cantina.modules.produto.dto;

import lombok.Data;

@Data
public class ProdutoResponseDTO {
    private String nome;
    private Double preco;
    private String descricao;
}
