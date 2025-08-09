package com.igrejacristahangar.cantina.modules.pedido.mapper;


import com.igrejacristahangar.cantina.modules.pedido.dto.PedidoResponseDTO;
import com.igrejacristahangar.cantina.modules.pedido.model.Pedido;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    PedidoResponseDTO PedidoToPedidoResponseDTO(Pedido pedido);

    List<PedidoResponseDTO> PedidoListToPedidoResponseDTOList(List<Pedido> pedidos);
}
