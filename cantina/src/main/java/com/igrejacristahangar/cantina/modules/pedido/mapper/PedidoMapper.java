package com.igrejacristahangar.cantina.modules.pedido.mapper;


import com.igrejacristahangar.cantina.modules.cliente.Cliente;
import com.igrejacristahangar.cantina.modules.cliente.dto.ClienteRequestDTO;
import com.igrejacristahangar.cantina.modules.pedido.dto.PedidoResponseDTO;
import com.igrejacristahangar.cantina.modules.pedido.model.Pedido;
import com.igrejacristahangar.cantina.modules.produto_pedido.dto.ProdutoPedidoResponseDTO;
import com.igrejacristahangar.cantina.modules.produto_pedido.model.ProdutoPedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    @Mapping(target = "cliente", source = "cliente")
    @Mapping(target = "itens", source = "itens")
    @Mapping(target = "abacateResponse", ignore = true)
    PedidoResponseDTO PedidoToPedidoResponseDTO(Pedido pedido);

    @Mapping(target = "taxId", source = "cpf")
    ClienteRequestDTO clienteToClienteRequestDTO(Cliente cliente);

    default ProdutoPedidoResponseDTO mapProdutoPedido(ProdutoPedido pp) {
        return ProdutoPedidoResponseDTO.builder()
                .nomeProduto(pp.getProduto().getNome())
                .quantidade(pp.getQuantidade())
                .preco(pp.getPreco())
                .build();
    }

    List<PedidoResponseDTO> PedidoListToPedidoResponseDTOList(List<Pedido> pedidos);
}
