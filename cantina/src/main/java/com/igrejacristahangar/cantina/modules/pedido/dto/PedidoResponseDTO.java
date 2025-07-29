package com.igrejacristahangar.cantina.modules.pedido.dto;

import java.time.LocalDateTime;

public class PedidoResponseDTO {
    String cliente_nome;
    Double valor;
    String forma_pagamento;
    LocalDateTime created_at;
    String status;
}
