package com.example.back.cart.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for adding items to a cart.
 * This class is used to receive data from clients for adding products to a shopping cart.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddToCartDTO {
    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
}