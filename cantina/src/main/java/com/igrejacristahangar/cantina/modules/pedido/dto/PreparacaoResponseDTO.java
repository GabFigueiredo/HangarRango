package com.igrejacristahangar.cantina.modules.pedido.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PreparacaoResponseDTO {

    private UUID id;
    private Integer numeroPedido;

    @Builder.Default
    private List<ProdutoPreparacaoDTO> listaProdutos = new ArrayList<>();

    public void adicionarNaLista(ProdutoPreparacaoDTO produto) {
        listaProdutos.add(produto);
    }

}
