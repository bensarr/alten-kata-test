package com.example.back.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object for shopping cart responses.
 * This class is used to send shopping cart data to clients.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartResponseDTO {
    private Long id;
    private Long userId;
    private List<CartItemDTO> items = new ArrayList<>();
    private Integer totalItems;
    private Double totalPrice;
    private Long createdAt;
    private Long updatedAt;
}