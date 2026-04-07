package com.igrejacristahangar.cantina.modules.produto_pedido.dto;


import com.igrejacristahangar.cantina.enterprise.order.Pedido;
import com.igrejacristahangar.cantina.enterprise.product.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoPedidoRequestDTO {
    private Pedido pedido;

    private Product produto;

    private Integer quantidade;

}
