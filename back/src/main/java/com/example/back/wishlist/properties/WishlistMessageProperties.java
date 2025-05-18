package com.example.back.wishlist.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "wishlist-messages")
@Configuration
@Data
public class WishlistMessageProperties {
    private String requestNullError;
    private String productIdNullError;
}
