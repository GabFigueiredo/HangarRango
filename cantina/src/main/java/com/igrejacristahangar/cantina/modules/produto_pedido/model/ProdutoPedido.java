package com.igrejacristahangar.cantina.modules.produto_pedido.model;

import java.math.BigDecimal;
import java.util.UUID;

import com.igrejacristahangar.cantina.modules.pedido.model.Pedido;
import com.igrejacristahangar.cantina.modules.produto.model.Produto;

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
    private Produto produto;

    @ManyToOne(optional = false)
    private Pedido pedido;

    private Integer quantidade;

    private BigDecimal preco;
    
}
