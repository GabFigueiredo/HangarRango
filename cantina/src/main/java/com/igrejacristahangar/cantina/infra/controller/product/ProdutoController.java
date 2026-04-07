package com.igrejacristahangar.cantina.infra.controller.product;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.igrejacristahangar.cantina.infra.controller.product.dto.ProdutoResponseDTO;
import com.igrejacristahangar.cantina.application.service.ProdutoService;

import java.util.List;


@RestController
@RequestMapping("/produto")
@Tag(name = "Produto", description = "Endpoints relacionados ao produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;


    @Operation(summary = "Busca todos os produtos disponíveis", method = "GET", description = "Rota responsável por buscar todos os produtos disponíveis")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca feita com sucesso.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProdutoResponseDTO.class)
                    )})
    })
    @GetMapping("/produtos-disponiveis")
    public ResponseEntity<List<ProdutoResponseDTO>> findAllAvailableProducts() {
        List<ProdutoResponseDTO> pl = produtoService.buscarTodosProdutosDisponiveis();

        pl.forEach(produtoResponseDTO -> {
            System.out.println(produtoResponseDTO.getNome());
        });

        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAA");

        return ResponseEntity.ok(pl);
    }
}