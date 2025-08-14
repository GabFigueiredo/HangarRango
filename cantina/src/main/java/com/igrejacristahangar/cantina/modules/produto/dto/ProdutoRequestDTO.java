package com.igrejacristahangar.cantina.modules.produto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

}
