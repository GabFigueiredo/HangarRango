package com.igrejacristahangar.cantina.modules.abacatepay.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Metadata {
    @NotNull(message = "O id externo do pedido não pode estar vazio")
    private UUID externalId;
}
