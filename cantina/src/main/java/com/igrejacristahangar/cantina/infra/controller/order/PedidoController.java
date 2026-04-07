package com.igrejacristahangar.cantina.infra.controller.order;

import com.igrejacristahangar.cantina.enterprise.order.Order;
import com.igrejacristahangar.cantina.enterprise.order.dto.PedidoRequestDTO;
import com.igrejacristahangar.cantina.enterprise.order.dto.PedidoResponseDTO;
import com.igrejacristahangar.cantina.enterprise.order.dto.StatusPagamentoDTO;
import com.igrejacristahangar.cantina.enterprise.order.dto.StatusRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import com.igrejacristahangar.cantina.enterprise.order.Pedido;
import com.igrejacristahangar.cantina.application.service.PedidoService;

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

        Order pedido = pedidoService.buscarPedidoPorID(requestDTO.getClientId());

        PedidoResponseDTO responseDTO = pedidoService.alterarStatusDePedido(pedido, requestDTO);

        return ResponseEntity.ok(responseDTO);
    }

    @PatchMapping("/pagamento")
    public ResponseEntity<PedidoResponseDTO> patch(@Valid @RequestBody StatusPagamentoDTO requestDTO) {
            return ResponseEntity.ok(pedidoService.alterarStatusDePagamento(requestDTO));
    }


}
