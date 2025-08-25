package com.igrejacristahangar.cantina.modules.produto.controller;

import com.igrejacristahangar.cantina.modules.produto.dto.StatusProdutoRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.igrejacristahangar.cantina.modules.produto.dto.ProdutoRequestDTO;
import com.igrejacristahangar.cantina.modules.produto.dto.ProdutoResponseDTO;
import com.igrejacristahangar.cantina.modules.produto.service.ProdutoService;

import java.util.List;


@RestController
@RequestMapping("/cantina/produto")
@Tag(name = "Produto", description = "Endpoints relacionados ao produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;


    @Operation(summary = "Cria um produto", method = "POST", description = "Rota responsável por criar um produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Criação de produto feita com sucesso.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProdutoResponseDTO.class)
                    )})
    })
    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> post(@Valid @RequestBody ProdutoRequestDTO produtoRequest) {

        return ResponseEntity.ok(produtoService.criarProduto(produtoRequest));
    }

    @Operation(summary = "Altera o status de um produto", method = "PATCH", description = "Rota responsável por alterar um status de um produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alteração de status feita com sucesso.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProdutoResponseDTO.class)
                    )})
    })
    @PatchMapping
    public ResponseEntity<ProdutoResponseDTO> patch(@Valid @RequestBody StatusProdutoRequestDTO requestDTO) {
        var produto = produtoService.encontrarProdutoPeloSeuID(requestDTO.getProdutoId());

        var responseDTO = produtoService.alterarStatusDoProduto(produto, requestDTO.getStatus());

        return ResponseEntity.ok(responseDTO);

    }

    @Operation(summary = "Busca todos os produtos", method = "GET", description = "Rota responsável por buscar todos os produtos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca feita com sucesso.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProdutoResponseDTO.class)
                    )})
    })
    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> findAllProducts() {
        return ResponseEntity.ok(produtoService.buscarTodosProdutos());
    }

    @Operation(summary = "Busca todos os produtos disponíveis", method = "GET", description = "Rota responsável por buscar todos os produtos disponíveis")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca feita com sucesso.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProdutoResponseDTO.class)
                    )})
    })
    @GetMapping("/produtos-disponiveis")
    public ResponseEntity<List<ProdutoResponseDTO>> findAllAvailableProducts() {
        return ResponseEntity.ok(produtoService.buscarTodosProdutosDisponiveis());
    }
}