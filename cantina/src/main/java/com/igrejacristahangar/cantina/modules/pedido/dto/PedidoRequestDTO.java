package com.igrejacristahangar.cantina.modules.pedido.dto;

import java.math.BigDecimal;
import java.util.List;

import com.igrejacristahangar.cantina.modules.pedido.enums.FORMA_PAGAMENTO;
import com.igrejacristahangar.cantina.modules.pedido.enums.STATUS_PAGAMENTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PedidoRequestDTO {

    @NotBlank(message = "O nome é obrigatório")
    private String clienteNome;

    @NotNull(message = "O preço é obrigatório")
    private BigDecimal preco;

    @NotNull(message = "A forma de pagamento é obrigatória")
    private FORMA_PAGAMENTO formaPagamento;

    @NotNull(message = "O status do pagamento é obrigatório")
    private STATUS_PAGAMENTO statusPagamento;

    @NotNull(message = "A lista de itens é obrigatória")
    List<DetalhesProdutoDTO> itens;

}
