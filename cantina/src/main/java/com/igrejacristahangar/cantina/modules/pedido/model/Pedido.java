package com.igrejacristahangar.cantina.modules.pedido.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.igrejacristahangar.cantina.modules.pedido.enums.STATUS;
import com.igrejacristahangar.cantina.modules.pedido.enums.STATUS_PAGAMENTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {
    
    @Id
    @GeneratedValue
    private UUID id;

    private String cliente_nome;
    
    private Double preco;

    private String forma_pagamento;

    private Integer numero_pedido;

    private STATUS status;

    private STATUS_PAGAMENTO status_pagamento;

    private LocalDateTime created_at;

}
