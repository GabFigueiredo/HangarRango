package com.igrejacristahangar.cantina.infra.controller.product.mapper;

import org.mapstruct.Mapper;

import com.igrejacristahangar.cantina.infra.controller.product.dto.ProdutoResponseDTO;
import com.igrejacristahangar.cantina.enterprise.product.Product;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    ProdutoResponseDTO ProductToResponse(Product produto);

    List<ProdutoResponseDTO> ProductListToResponseList(List<Product> lista);

}
