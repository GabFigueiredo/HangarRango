package com.igrejacristahangar.cantina.enterprise.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoPreparacaoDTO {
    private String nome;
    private Integer quantidade;
}
