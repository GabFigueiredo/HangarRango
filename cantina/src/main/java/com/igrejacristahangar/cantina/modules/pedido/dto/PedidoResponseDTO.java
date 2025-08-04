package com.igrejacristahangar.cantina.modules.pedido.dto;

import com.igrejacristahangar.cantina.modules.pedido.enums.FORMA_PAGAMENTO;
import com.igrejacristahangar.cantina.modules.pedido.enums.STATUS;
import com.igrejacristahangar.cantina.modules.pedido.enums.STATUS_PAGAMENTO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PedidoResponseDTO {
    private String clienteNome;
    private Double preco;
    private STATUS_PAGAMENTO statusPagamento;
    private FORMA_PAGAMENTO formaPagamento;
    private Integer numeroPedido;
    private LocalDateTime createdAt;
    private STATUS status;
}
