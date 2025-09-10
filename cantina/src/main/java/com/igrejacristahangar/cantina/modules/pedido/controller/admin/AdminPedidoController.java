package com.igrejacristahangar.cantina.modules.pedido.controller.admin;

import com.igrejacristahangar.cantina.modules.pedido.dto.*;
import com.igrejacristahangar.cantina.modules.pedido.enums.FORMA_PAGAMENTO;
import com.igrejacristahangar.cantina.modules.pedido.enums.STATUS;
import com.igrejacristahangar.cantina.modules.pedido.enums.STATUS_PAGAMENTO;
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

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/admin/pedido")
@Tag(name = "Admin Pedido", description = "Endpoints protegidos relacionados ao pedido.")
public class AdminPedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Operation(summary = "Busca pedidos por paginação", method = "GET", description = "Rota responsável pela busca de pedidos por paginação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca de Pedidos feita com sucesso!",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PedidoResponseDTO.class)
                    )})
    })
    @GetMapping("/pedidos-paginados")
    public ResponseEntity<PagedModel<EntityModel<Pedido>>> get(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, PagedResourcesAssembler<Pedido> assembler) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Pedido> pedidos = pedidoService.buscarPedidosPorRange(pageable);

        PagedModel<EntityModel<Pedido>> model = assembler.toModel(pedidos,
                pedido -> EntityModel.of(pedido,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(getClass()).get(page, size, assembler)).withSelfRel()
                ));

        return ResponseEntity.ok(model);
    }

    @GetMapping("/pedidos-pendentes")
    public ResponseEntity<List<PedidoResponseDTO>> getAllPreparingOrders() {
        return ResponseEntity.ok(pedidoService.buscarTodosPedidosPreparando());
    }

    @Operation(summary = "Busca todos os pedidos no banco de dados", method = "GET", description = "Rota responsável por buscar todos os pedidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca de Pedidos feita com sucesso!",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PedidoResponseDTO.class)
                    )})
    })
    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> getAllOrders() {
        return ResponseEntity.ok(pedidoService.buscarTodosOsPedidos());
    }

    @Operation(summary = "Busca pedidos filtrados por paginação", method = "GET", description = "Rota responsável pela busca de pedidos filtrados por paginação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca de Pedidos filtrados feita com sucesso!",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PedidoResponseDTO.class)
                    )})
    })
    @GetMapping("/filtrar")
    public ResponseEntity<PagedModel<EntityModel<Pedido>>> getOrdersByFilter(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            PagedResourcesAssembler<Pedido> assembler,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) LocalDate data,
            @RequestParam(required = false) STATUS pedidoStatus,
            @RequestParam(required = false) FORMA_PAGAMENTO formaPagamento,
            @RequestParam(required = false) STATUS_PAGAMENTO statusPagamento
    ) {
        FiltroPedidoDTO filtro = FiltroPedidoDTO.builder()
                .nome(nome)
                .data(data)
                .pedidoStatus(pedidoStatus)
                .formaPagamento(formaPagamento)
                .statusPagamento(statusPagamento)
                .build();

        Pageable pageable = PageRequest.of(page, size);
        Page<Pedido> pedidos = pedidoService.buscarPedidosPorTodosOsFiltros(filtro, pageable);

        PagedModel<EntityModel<Pedido>> model = assembler.toModel(pedidos,
                pedido -> EntityModel.of(pedido,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(getClass()).get(page, size, assembler)).withSelfRel()
                ));

        return ResponseEntity.ok(model);
    }

    @Operation(summary = "Busca o resumo de vendas", method = "GET", description = "Rota responsável pela busca do resumo de vendas: Total vendido e total vendido hoje")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca o resumo de vendas feita com sucesso!",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PedidoResumo.class)
                    )})
    })
    @GetMapping("/resumo")
    public ResponseEntity<PedidoResumo> getSumary() {
        return ResponseEntity.ok(pedidoService.gerarResumoDeVendas());
    }


    @Operation(summary = "Altera o status do pedido", method = "PATCH", description = "Rota responsável pela alteração de status de pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alteração de status de pedido feita com sucesso!",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PedidoResponseDTO.class)
                    )})
    })
    @PatchMapping("/status")
    public ResponseEntity<PedidoResponseDTO> patch(@Valid @RequestBody StatusRequestDTO requestDTO) {

        Pedido pedido = pedidoService.buscarPedidoPorID(requestDTO.getClientId());

        PedidoResponseDTO responseDTO = pedidoService.alterarStatusDePedido(pedido, requestDTO);

        return ResponseEntity.ok(responseDTO);
    }


}
