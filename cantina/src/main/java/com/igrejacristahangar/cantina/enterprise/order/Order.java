package com.igrejacristahangar.cantina.enterprise.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.igrejacristahangar.cantina.enterprise.Cliente;
import com.igrejacristahangar.cantina.enterprise.model.ProdutoPedido;
import com.igrejacristahangar.cantina.infra.controller.order.enums.FORMA_PAGAMENTO;
import com.igrejacristahangar.cantina.infra.controller.order.enums.STATUS;
import com.igrejacristahangar.cantina.infra.controller.order.enums.STATUS_PAGAMENTO;
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
public class Order {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private Cliente cliente;

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
