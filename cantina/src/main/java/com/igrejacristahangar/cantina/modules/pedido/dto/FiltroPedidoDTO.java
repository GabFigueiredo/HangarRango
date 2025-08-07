package com.igrejacristahangar.cantina.modules.pedido.dto;

import com.igrejacristahangar.cantina.modules.pedido.enums.FORMA_PAGAMENTO;
import com.igrejacristahangar.cantina.modules.pedido.enums.STATUS;
import com.igrejacristahangar.cantina.modules.pedido.enums.STATUS_PAGAMENTO;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class FiltroPedidoDTO {
    private String nome;
    private LocalDate data;
    private STATUS pedidoStatus;
    private STATUS status;
    private FORMA_PAGAMENTO formaPagamento;
    private STATUS_PAGAMENTO statusPagamento;

}
