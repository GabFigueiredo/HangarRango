package com.igrejacristahangar.cantina.modules.pedido.dto;

import com.igrejacristahangar.cantina.modules.pedido.enums.STATUS;
import com.igrejacristahangar.cantina.modules.pedido.enums.STATUS_PAGAMENTO;
import lombok.Data;

import java.util.UUID;

@Data
public class StatusRequestDTO {
    private UUID clientId;
    private STATUS pedidoStatus;
    private STATUS_PAGAMENTO statusPagamento;
}
