package com.igrejacristahangar.cantina.modules.abacatepay.controller;

import com.igrejacristahangar.cantina.modules.abacatepay.dto.CreatePixQrCodeDTO;
import com.igrejacristahangar.cantina.modules.abacatepay.dto.PixQrCodeResponseDTO;
import com.igrejacristahangar.cantina.modules.abacatepay.service.AbacateService;
import com.igrejacristahangar.cantina.modules.pedido.dto.PedidoResponseDTO;
import com.igrejacristahangar.cantina.modules.pedido.enums.STATUS;
import com.igrejacristahangar.cantina.modules.pedido.enums.STATUS_PAGAMENTO;
import com.igrejacristahangar.cantina.modules.pedido.mapper.PedidoMapper;
import com.igrejacristahangar.cantina.modules.pedido.model.Pedido;
import com.igrejacristahangar.cantina.modules.pedido.repository.PedidoRepository;
import com.igrejacristahangar.cantina.modules.pedido.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
@RestController
@RequestMapping("/abacate/webhook")
@Tag(name = "AbacatePay", description = "Rotas para o checkout de pagamento.")
public class AbacateController {

    @Autowired
    AbacateService abacateService;

    @Autowired
    PedidoService pedidoService;

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    private PedidoMapper pedidoMapper;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Operation(summary = "Webhook AbacatePay", description = "Rota responsável por receber o webhook da abacatepay", method = "POST")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Webhook recebido com sucesso.")
            }
    )
    @PostMapping
    public ResponseEntity<Void> WebHookAbacatePay(@RequestBody PixQrCodeResponseDTO payload) {
        Pedido pedido = pedidoService.buscarPedidoPorID(payload.getData().getMetadata().getExternalId());

        if (Objects.equals(payload.getData().getStatus(), "PAID")) {
            pedido.setStatusPagamento(STATUS_PAGAMENTO.EFETUADO);
            pedido.setStatus(STATUS.PREPARANDO);

            // Mapeia para DTO de resposta
            PedidoResponseDTO pedidoMapeado = pedidoMapper.PedidoToPedidoResponseDTO(pedido);
            pedidoMapeado.setAbacateResponse(payload);

            messagingTemplate.convertAndSend("/cantina/preparacao", pedidoMapeado);

            pedidoRepository.save(pedido);
        }

        return ResponseEntity.ok().build();
    }


}
