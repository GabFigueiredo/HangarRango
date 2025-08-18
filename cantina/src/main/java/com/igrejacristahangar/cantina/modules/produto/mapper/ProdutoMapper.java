package com.igrejacristahangar.cantina.modules.produto.mapper;

import org.mapstruct.Mapper;

import com.igrejacristahangar.cantina.modules.produto.dto.ProdutoResponseDTO;
import com.igrejacristahangar.cantina.modules.produto.model.Produto;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    ProdutoResponseDTO ProductToResponse(Produto produto);

    List<ProdutoResponseDTO> ProductListToResponseList(List<Produto> lista);

}
