package com.igrejacristahangar.cantina.modules.produto.mapper;

import org.mapstruct.Mapper;

import com.igrejacristahangar.cantina.modules.produto.dto.ProdutoResponseDTO;
import com.igrejacristahangar.cantina.modules.produto.model.Produto;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {
    
    ProdutoResponseDTO ProductToResponse(Produto produto);

}
