package com.igrejacristahangar.cantina.modules.produto_pedido.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoPedidoResponseDTO {
    private String nomeProduto;
    private BigDecimal preco;
    private Integer quantidade;
}
