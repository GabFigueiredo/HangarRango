package com.igrejacristahangar.cantina.modules.pedido.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PedidoResumo {
    private Double totalPreco;
    private Double totalPrecoDeHoje;
 }
