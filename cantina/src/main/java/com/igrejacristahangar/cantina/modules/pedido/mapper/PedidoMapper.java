package com.igrejacristahangar.cantina.modules.pedido.mapper;


import com.igrejacristahangar.cantina.modules.pedido.dto.PedidoResponseDTO;
import com.igrejacristahangar.cantina.modules.pedido.model.Pedido;
import com.igrejacristahangar.cantina.modules.produto_pedido.dto.ProdutoPedidoResponseDTO;
import com.igrejacristahangar.cantina.modules.produto_pedido.model.ProdutoPedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    @Mapping(target = "itens", source = "itens")
    PedidoResponseDTO PedidoToPedidoResponseDTO(Pedido pedido);

    default ProdutoPedidoResponseDTO mapProdutoPedido(ProdutoPedido pp) {
        return ProdutoPedidoResponseDTO.builder()
                .nomeProduto(pp.getProduto().getNome())
                .quantidade(pp.getQuantidade())
                .preco(pp.getPreco())
                .build();
    }

    List<PedidoResponseDTO> PedidoListToPedidoResponseDTOList(List<Pedido> pedidos);
}
