package com.example.back.cart.dto.response;

import com.example.back.cart.dto.request.CartItemRequestDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("user_id")
    private Long userId;
    private List<CartItemRequestDTO> items = new ArrayList<>();
    @JsonProperty("total_items")
    private Integer totalItems;
    @JsonProperty("total_price")
    private Double totalPrice;
    @JsonProperty("created_at")
    private Long createdAt;
    @JsonProperty("updated_at")
    private Long updatedAt;
}