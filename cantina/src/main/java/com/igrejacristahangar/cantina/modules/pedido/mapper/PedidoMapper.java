package com.igrejacristahangar.cantina.modules.pedido.mapper;

import org.mapstruct.Mapper;

import com.igrejacristahangar.cantina.modules.pedido.dto.PedidoResponseDTO;
import com.igrejacristahangar.cantina.modules.pedido.model.Pedido;

// componentModel = "spring" permite injetar com @Autowired
@Mapper(componentModel = "spring")
public interface PedidoMapper {
    
    PedidoResponseDTO PedidoToPedidoResponseDTO(Pedido pedido);

}
