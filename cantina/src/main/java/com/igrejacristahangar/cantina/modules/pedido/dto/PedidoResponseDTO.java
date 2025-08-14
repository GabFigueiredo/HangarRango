package com.igrejacristahangar.cantina.modules.pedido.dto;

import com.igrejacristahangar.cantina.modules.pedido.enums.FORMA_PAGAMENTO;
import com.igrejacristahangar.cantina.modules.pedido.enums.STATUS;
import com.igrejacristahangar.cantina.modules.pedido.enums.STATUS_PAGAMENTO;
import com.igrejacristahangar.cantina.modules.produto_pedido.dto.ProdutoPedidoResponseDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class PedidoResponseDTO {
    private UUID id;
    private String clienteNome;
    private BigDecimal preco;
    private STATUS_PAGAMENTO statusPagamento;
    private FORMA_PAGAMENTO formaPagamento;
    private Integer numeroPedido;
    private LocalDateTime createdAt;
    private STATUS status;

    private List<ProdutoPedidoResponseDTO> itens;
}
