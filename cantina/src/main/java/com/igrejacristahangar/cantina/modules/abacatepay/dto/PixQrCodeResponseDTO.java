package com.igrejacristahangar.cantina.modules.abacatepay.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PixQrCodeResponseDTO {
    private DataDTO data;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DataDTO {
        private String id;
        private BigDecimal amount;
        private String status;
        private Boolean devMode;
        private String brCode;
        private String brCodeBase64;
        private BigDecimal platformFee;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private LocalDateTime expiresAt;
        private Metadata metadata;
    }
    private String error;
}
