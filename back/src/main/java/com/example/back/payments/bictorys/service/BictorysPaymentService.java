package com.example.back.payments.bictorys.service;

import com.example.back.common.exception.PaymentException;
import com.example.back.payments.bictorys.dto.request.BictorysPaymentRequestDTO;
import com.example.back.payments.bictorys.dto.response.BictorysPaymentResponseDTO;
import com.example.back.payments.bictorys.properties.BictorysPaymentProviderProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class BictorysPaymentService {

    private final RestTemplate restTemplate;
    private final BictorysPaymentProviderProperties properties;

    public BictorysPaymentService(RestTemplate restTemplate, BictorysPaymentProviderProperties properties) {
        this.restTemplate = restTemplate;
        this.properties = properties;
    }

    /**
     * Crée un paiement checkout avec Bictorys
     * @param request Les données de paiement
     * @return La réponse de l'API contenant l'URL de redirection
     */
    public BictorysPaymentResponseDTO createCheckoutPayment(BictorysPaymentRequestDTO request) throws PaymentException {
        try {
            log.info("properties value: {}", properties.toString());
            log.info("request value: {}", request.toString());
            // Préparer les headers
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            headers.add(properties.getPublicApiKey(), properties.getPublicApiValue());
            log.info("headers value: {}", headers);

            // Créer l'entité HTTP avec le corps et les headers
            HttpEntity<BictorysPaymentRequestDTO> entity = new HttpEntity<>(request, headers);

            // Envoyer la requête POST
            ResponseEntity<BictorysPaymentResponseDTO> response = restTemplate.exchange(
                    properties.getApiUrl(),
                    HttpMethod.POST,
                    entity,
                    BictorysPaymentResponseDTO.class
            );
            log.info("response code: {}", response.getStatusCode());
            if (response.getStatusCode() == HttpStatus.ACCEPTED && response.getBody() != null) {
                log.info("Paiement créé avec succès: {}", response.getBody());
                return response.getBody();
            } else {
                throw new PaymentException("Erreur lors de la création du paiement");
            }

        } catch (Exception e) {
            log.error("Erreur lors de l'appel à l'API Bictorys", e);
            throw new PaymentException("Erreur lors de la création du paiement: " + e.getMessage());
        }
    }
}