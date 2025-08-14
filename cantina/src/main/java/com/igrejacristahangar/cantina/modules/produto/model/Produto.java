package com.igrejacristahangar.cantina.modules.produto.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.igrejacristahangar.cantina.modules.produto_pedido.model.ProdutoPedido;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;
    private BigDecimal preco;
    private boolean status;
    private String descricao;
    private Integer quantidade;

    @OneToMany(mappedBy = "produto")
    private List<ProdutoPedido> pedidos;

}
