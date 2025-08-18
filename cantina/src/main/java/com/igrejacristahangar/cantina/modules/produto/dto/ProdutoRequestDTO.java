package com.igrejacristahangar.cantina.modules.produto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoRequestDTO {

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotNull(message = "O preço é obrigatório")
    private BigDecimal preco;

    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;

    @NotNull(message = "A quantidade a ser vendida é obrigatória")
    @Positive(message = "A quantidade a ser vendida não pode ser negativa")
    private Integer quantidade;

    @NotNull(message = "O Status do produto é obrigatório")
    private Boolean status;
}
