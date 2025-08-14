package com.igrejacristahangar.cantina.modules.pedido.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PedidoResumo {
    private BigDecimal totalPreco;
    private BigDecimal totalPrecoDeHoje;
 }
