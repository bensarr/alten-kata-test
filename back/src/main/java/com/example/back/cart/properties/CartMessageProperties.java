package com.example.back.cart.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "cart-messages")
@Configuration
@Data
public class CartMessageProperties {
    private int minQuantity;
    private String notEnoughStock;
    private String productOutOfStock;
    private String quantityMustBePositive;
    private String noAuthentication;
    private String userNullError;
    private String cartCreateNullError;
    private String cartUpdateNullError;
    private String productNullError;
}
