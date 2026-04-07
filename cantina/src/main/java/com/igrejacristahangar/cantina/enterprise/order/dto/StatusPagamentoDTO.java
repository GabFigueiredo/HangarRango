package com.igrejacristahangar.cantina.enterprise.order.dto;

import com.igrejacristahangar.cantina.infra.controller.order.enums.STATUS_PAGAMENTO;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class StatusPagamentoDTO {
    @NotNull(message = "O id não pode estar vazio")
    private UUID id;
    @NotNull(message = "O status não pode estar vazio")
    private STATUS_PAGAMENTO status;
}
