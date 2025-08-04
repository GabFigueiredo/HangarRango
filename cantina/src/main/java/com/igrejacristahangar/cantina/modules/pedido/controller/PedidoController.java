package com.igrejacristahangar.cantina.modules.pedido.controller;

import com.igrejacristahangar.cantina.modules.pedido.dto.PedidoRequestDTO;
import com.igrejacristahangar.cantina.modules.pedido.dto.PedidoResponseDTO;
import com.igrejacristahangar.cantina.modules.pedido.dto.StatusRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import com.igrejacristahangar.cantina.modules.pedido.model.Pedido;
import com.igrejacristahangar.cantina.modules.pedido.service.PedidoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/cantina")
@Tag(name = "Pedido", description = "Endpoints relacionados ao pedido.")
public class PedidoController {
    
    @Autowired
    private PedidoService pedidoService;




    @Operation(summary = "Busca pedidos por paginação", method = "GET", description = "Rota responsável pela busca de pedidos por paginação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca de Pedidos feita com sucesso!",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PedidoResponseDTO.class)
                    )})
    })
    @GetMapping("/pedido")
    public ResponseEntity<PagedModel<EntityModel<Pedido>>> get(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, PagedResourcesAssembler<Pedido> assembler) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Pedido> pedidos = pedidoService.buscarPedidosPorRange(pageable);

        PagedModel<EntityModel<Pedido>> model = assembler.toModel(pedidos,
                pedido -> EntityModel.of(pedido,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(getClass()).get(page, size, assembler)).withSelfRel()
                ));

        return ResponseEntity.ok(model);
    }

    @Operation(summary = "Criar pedido", method = "POST", description = "Rota responsável pela criação de um pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Criação de Pedido feita com sucesso!",
                content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = PedidoResponseDTO.class)
                )})
    })
    @PostMapping("/pedido")
    public ResponseEntity<PedidoResponseDTO> post(@Valid @RequestBody PedidoRequestDTO requestDTO) {

        var responseDTO = pedidoService.criarPedido(requestDTO);

        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "Altera o status de pedido, e o status de pagamento", method = "PATCH", description = "Rota responsável pela alteração de status de pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alteração de status de pedido feita com sucesso!",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PedidoResponseDTO.class)
                    )})
    })
    @PatchMapping("/pedido")
    public ResponseEntity<PedidoResponseDTO> patch(@Valid StatusRequestDTO requestDTO) {

        Pedido pedido = pedidoService.buscarPedidoPorID(requestDTO.getClientId());

        PedidoResponseDTO responseDTO = pedidoService.alterarStatusDePedidoEPagamento(pedido, requestDTO);

        return ResponseEntity.ok(responseDTO);
    }

}
