package com.igrejacristahangar.cantina.modules.pedido.controller;

import org.springframework.web.bind.annotation.RestController;

import com.igrejacristahangar.cantina.modules.pedido.model.Pedido;
import com.igrejacristahangar.cantina.modules.pedido.service.PedidoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/cantina")
public class PedidoController {
    
    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<Page<Pedido>> get(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);

        Page<Pedido> pedidos = pedidoService.buscarPedidosPorRange(pageable);

        return ResponseEntity.ok(pedidos);
    }

    

}
