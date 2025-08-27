package com.igrejacristahangar.cantina.modules.pedido.dto;

import com.igrejacristahangar.cantina.modules.pedido.enums.STATUS_PAGAMENTO;
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
