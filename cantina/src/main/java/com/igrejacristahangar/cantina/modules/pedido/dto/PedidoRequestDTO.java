package com.igrejacristahangar.cantina.modules.pedido.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PedidoRequestDTO {
    
    @NotBlank
    String cliente_nome;
    Double preco;
    String forma_pagamento;
    String status_pagamento;

    List<DetalhesProdutoDTO> detalhesProdutos;

}
