package com.igrejacristahangar.cantina.modules.abacatepay.dto;

import com.igrejacristahangar.cantina.modules.cliente.dto.ClienteRequestDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePixQrCodeDTO {
    @NotNull(message = "A quantia não pode estar vazia")
    private Integer amount;

    @NotNull(message = "O cliente não pode estar vazio")
    private ClienteRequestDTO customer;

    private Metadata metadata;
}
