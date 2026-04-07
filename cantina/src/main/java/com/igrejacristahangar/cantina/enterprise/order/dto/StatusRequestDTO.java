package com.igrejacristahangar.cantina.enterprise.order.dto;

import com.igrejacristahangar.cantina.infra.controller.order.enums.STATUS;
import com.igrejacristahangar.cantina.infra.controller.order.enums.STATUS_PAGAMENTO;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class StatusRequestDTO {
    @NotNull(message = "ID não pode estar vazio")
    private UUID clientId;

    private STATUS pedidoStatus;
    private STATUS_PAGAMENTO statusPagamento;
}
