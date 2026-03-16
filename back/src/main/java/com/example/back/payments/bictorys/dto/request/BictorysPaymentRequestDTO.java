package com.example.back.payments.bictorys.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO pour la création d'un paiement Bictorys Checkout
 * Seuls amount et currency sont obligatoires
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BictorysPaymentRequestDTO {

    /**
     * Montant du paiement (obligatoire)
     * Exemple: 5000 pour 5000 XOF
     */
    @NotNull(message = "Le montant est obligatoire")
    @Positive(message = "Le montant doit être positif")
    private Double amount;

    /**
     * Code devise (obligatoire)
     * Exemples: "XOF", "EUR", "USD", "GNF"
     */
    @NotNull(message = "La devise est obligatoire")
    private String currency;

    /**
     * Référence de votre commande/transaction (optionnel)
     * Exemple: "ORDER_2025_001"
     */
    private String paymentReference;

    /**
     * Référence unique pour le marchand (optionnel)
     * Exemple: "33e1c83b-7cb0-437b-bc50-a7a58e5660ad"
     */
    private String merchantReference;

    /**
     * URL de redirection en cas de succès (recommandé)
     * Exemple: "<a href="https://votresite.com/payments/success">...</a>"
     */
    private String successRedirectUrl;

    /**
     * URL de redirection en cas d'erreur (recommandé)
     * Exemple: "<a href="https://votresite.com/payments/error">...</a>"
     */
    private String errorRedirectUrl;

    /**
     * Liste des articles/produits de la commande (optionnel)
     */
    private List<OrderDetailDTO> orderDetails;

    /**
     * Informations sur le client (optionnel)
     */
    private CustomerDTO customerObject;

    /**
     * Autoriser la mise à jour des informations client (optionnel)
     * Par défaut: false
     */
    private Boolean allowUpdateCustomer;

    /**
     * DTO pour les détails d'un article/produit
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class OrderDetailDTO {

        /**
         * Nom du produit
         * Exemple: "iPhone 15 Pro"
         */
        private String name;

        /**
         * Prix unitaire du produit
         * Exemple: 8000.0
         */
        private Double price;

        /**
         * Quantité
         * Exemple: 2.5 (peut être décimal)
         */
        private Double quantity;

        /**
         * Taux de taxe en pourcentage
         * Exemple: 18.0 pour 18%
         */
        private Double taxRate;
    }

    /**
     * DTO pour les informations du client
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class CustomerDTO {

        /**
         * Nom complet du client
         * Exemple: "Mamadou Diallo"
         */
        private String name;

        /**
         * Numéro de téléphone (format international recommandé)
         * Exemple: "+221776794638"
         */
        private String phone;

        /**
         * Adresse email
         * Exemple: "mamadou.diallo@example.com"
         */
        private String email;

        /**
         * Ville
         * Exemple: "Dakar"
         */
        private String city;

        /**
         * Code postal
         * Exemple: "12000"
         */
        @JsonProperty("postal_code")
        private String postalCode;

        /**
         * Code pays ISO 3166-1 alpha-2
         * Exemples: "SN" (Sénégal), "FR" (France), "CI" (Côte d'Ivoire)
         */
        private String country;

        /**
         * Locale (langue-PAYS)
         * Exemples: "fr-FR", "en-US", "fr-SN"
         */
        private String locale;
    }
}