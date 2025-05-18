package com.example.back.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for cart items.
 * This class is used to send cart item data to clients.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
    private Long productId;
    private String productCode;
    private String productName;
    private String productImage;
    private Double productPrice;
    private Integer quantity;
    private Double subtotal;
}