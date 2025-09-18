package com.igrejacristahangar.cantina.modules.abacatepay.service;

import com.igrejacristahangar.cantina.modules.abacatepay.dto.CreatePixQrCodeDTO;
import com.igrejacristahangar.cantina.modules.abacatepay.dto.PixQrCodeResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class AbacateService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${abacate.api.key}")
    private String abacateApiKey;

    public PixQrCodeResponseDTO criarPixQrCode(CreatePixQrCodeDTO payload) {
        String url = "https://api.abacatepay.com/v1/pixQrCode/create";

        // headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + abacateApiKey);

        HttpEntity<CreatePixQrCodeDTO> request = new HttpEntity<CreatePixQrCodeDTO>(payload, headers);

        return restTemplate.postForObject(url, request, PixQrCodeResponseDTO.class);

    }

    public String simulatePayment(String pixId) {
        String url = "https://api.abacatepay.com/v1/pixQrCode/simulate-payment/" + pixId;

        // headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + abacateApiKey);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        return restTemplate.postForObject(url, request, String.class);
    }

    public String checkStatus(String pixId) {
        String url = "https://api.abacatepay.com/v1/pixQrCode/check" + pixId;

        // headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + abacateApiKey);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        return restTemplate.postForObject(url, request, String.class);
    }
}