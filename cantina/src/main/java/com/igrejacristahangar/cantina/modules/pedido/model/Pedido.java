package com.igrejacristahangar.cantina.modules.pedido.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.igrejacristahangar.cantina.modules.pedido.enums.FORMA_PAGAMENTO;
import com.igrejacristahangar.cantina.modules.pedido.enums.STATUS;
import com.igrejacristahangar.cantina.modules.pedido.enums.STATUS_PAGAMENTO;
import com.igrejacristahangar.cantina.modules.produto_pedido.model.ProdutoPedido;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue
    private UUID id;

    private String clienteNome;

    private BigDecimal preco;

    private Integer numeroPedido;

    @Enumerated(EnumType.STRING)
    private STATUS status;

    @Enumerated(EnumType.STRING)
    private STATUS_PAGAMENTO statusPagamento;

    @Enumerated(EnumType.STRING)
    private FORMA_PAGAMENTO formaPagamento;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProdutoPedido> itens;

}
