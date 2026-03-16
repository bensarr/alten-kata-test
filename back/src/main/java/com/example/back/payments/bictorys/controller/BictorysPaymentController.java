package com.example.back.payments.bictorys.controller;
import com.example.back.payments.bictorys.dto.request.BictorysPaymentRequestDTO;
import com.example.back.payments.bictorys.dto.response.BictorysPaymentResponseDTO;
import com.example.back.payments.bictorys.service.BictorysPaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Validated
public class BictorysPaymentController {

    private final BictorysPaymentService paymentService;

    /**
     * Endpoint pour créer un paiement checkout
     * POST /api/payments/checkout
     */
    @PostMapping("/checkout")
    public ResponseEntity<BictorysPaymentResponseDTO> createCheckout(
            @Valid @RequestBody BictorysPaymentRequestDTO request) {

        log.info("Création d'un paiement checkout pour le montant: {} {}",
                request.getAmount(), request.getCurrency());

        try {
            BictorysPaymentResponseDTO response = paymentService.createCheckoutPayment(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Erreur lors de la création du paiement", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}