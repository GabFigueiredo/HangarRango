package com.igrejacristahangar.cantina.enterprise.model;

import java.math.BigDecimal;
import java.util.UUID;

import com.igrejacristahangar.cantina.enterprise.order.Order;
import com.igrejacristahangar.cantina.enterprise.product.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProdutoPedido {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false)
    private Product product;

    @ManyToOne(optional = false)
    private Order order;

    private Integer quantidade;

    private BigDecimal preco;
    
}
