package com.igrejacristahangar.cantina.modules.produto_pedido.dto;


import com.igrejacristahangar.cantina.modules.pedido.model.Pedido;
import com.igrejacristahangar.cantina.modules.produto.model.Produto;

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

    private Produto produto;

    private Integer quantidade;

}
