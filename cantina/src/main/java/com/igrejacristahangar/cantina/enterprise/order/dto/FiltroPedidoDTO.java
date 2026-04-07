package com.igrejacristahangar.cantina.enterprise.order.dto;

import com.igrejacristahangar.cantina.infra.controller.order.enums.FORMA_PAGAMENTO;
import com.igrejacristahangar.cantina.infra.controller.order.enums.STATUS;
import com.igrejacristahangar.cantina.infra.controller.order.enums.STATUS_PAGAMENTO;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class FiltroPedidoDTO {
    private String nome;
    private LocalDate data;
    private STATUS pedidoStatus;
    private FORMA_PAGAMENTO formaPagamento;
    private STATUS_PAGAMENTO statusPagamento;

}
