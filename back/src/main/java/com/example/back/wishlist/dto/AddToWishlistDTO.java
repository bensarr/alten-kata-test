package com.example.back.wishlist.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for adding items to a wishlist.
 * This class is used to receive data from clients for adding products to a wishlist.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddToWishlistDTO {
    @NotNull(message = "Product ID is required")
    @JsonProperty("product_id")
    private Long productId;
}