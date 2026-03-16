package com.example.back.payments.bictorys.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO pour la réponse de l'API Bictorys après création d'un paiement
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BictorysPaymentResponseDTO {

    /**
     * Identifiant unique de la transaction Bictorys
     * Exemple: "charge_abc123xyz789"
     */
    private String chargeId;

    /**
     * Statut du paiement
     * Valeurs possibles: "pending", "success", "failed", "cancelled"
     */
    private String status;

    /**
     * URL de redirection vers la page de checkout Bictorys
     * C'est l'URL vers laquelle vous devez rediriger votre utilisateur
     * Exemple: "<a href="https://checkout.bictorys.com/pay/abc123xyz789">...</a>"
     */
    private String checkoutUrl;

    /**
     * Montant du paiement
     * Exemple: 10000.0
     */
    private Double amount;

    /**
     * Devise du paiement
     * Exemple: "XOF"
     */
    private String currency;

    /**
     * Référence de paiement du marchand
     * Exemple: "ORDER_2025_001"
     */
    private String paymentReference;

    /**
     * Référence unique du marchand
     * Exemple: "33e1c83b-7cb0-437b-bc50-a7a58e5660ad"
     */
    private String merchantReference;

    /**
     * Date et heure de création de la transaction
     * Format ISO 8601
     * Exemple: "2025-10-05T14:30:00Z"
     */
    private String createdAt;

    /**
     * Message descriptif de l'API
     * Exemple: "Charge créée avec succès"
     */
    private String message;

    /**
     * Informations sur le client (si présentes)
     */
    private CustomerInfoDTO customer;

    /**
     * Détails de la commande (si présents)
     */
    private OrderInfoDTO order;
}

/**
 * DTO pour les informations client dans la réponse
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class CustomerInfoDTO {
    private String name;
    private String email;
    private String phone;
    private String city;
    private String country;
}

/**
 * DTO pour les informations de commande dans la réponse
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class OrderInfoDTO {
    private String orderId;
    private Double totalAmount;
    private String status;
}