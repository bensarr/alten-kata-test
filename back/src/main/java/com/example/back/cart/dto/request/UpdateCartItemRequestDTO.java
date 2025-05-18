package com.example.back.cart.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for updating cart items.
 * This class is used to receive data from clients for updating items in a shopping cart.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCartItemRequestDTO {
    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
}