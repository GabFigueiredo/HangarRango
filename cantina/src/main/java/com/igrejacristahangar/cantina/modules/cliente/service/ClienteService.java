package com.igrejacristahangar.cantina.modules.cliente.service;

import com.igrejacristahangar.cantina.exceptions.ResourceNotFoundException;
import com.igrejacristahangar.cantina.modules.cliente.Cliente;
import com.igrejacristahangar.cantina.modules.cliente.dto.ClienteRequestDTO;
import com.igrejacristahangar.cantina.modules.cliente.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    public Cliente findClienteByCpf(String cpf) {
        return clienteRepository.findClienteByCpf(cpf).orElseThrow(
                () -> new ResourceNotFoundException("Cliente não encontrado", "cliente"));
    }

    public Cliente createClient(ClienteRequestDTO payload) {
        Cliente novoCliente = Cliente.builder()
                .name(payload.getName())
                .email(payload.getEmail())
                .cellphone(payload.getCellphone())
                .cpf(payload.getTaxId())
                .build();

        return clienteRepository.save(novoCliente);
    }
}
