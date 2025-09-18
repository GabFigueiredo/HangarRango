package com.igrejacristahangar.cantina.modules.pedido.controller;

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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedido")
@Tag(name = "Pedido", description = "Endpoints relacionados ao pedido.")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Operation(summary = "Cria um pedido", method = "POST", description = "Rota responsável pela criação de um pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Criação pedido feita com sucesso",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PedidoResponseDTO.class)
                    )})
    })
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> post(@Valid @RequestBody PedidoRequestDTO requestDTO) {

        var responseDTO = pedidoService.criarPedido(requestDTO);

        return ResponseEntity.ok(responseDTO);
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

    @PatchMapping("/pagamento")
    public ResponseEntity<PedidoResponseDTO> patch(@Valid @RequestBody StatusPagamentoDTO requestDTO) {
            return ResponseEntity.ok(pedidoService.alterarStatusDePagamento(requestDTO));
    }


}
