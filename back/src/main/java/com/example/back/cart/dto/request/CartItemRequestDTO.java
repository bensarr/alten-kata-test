package com.example.back.cart.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class CartItemRequestDTO {
    @JsonProperty("product_id")
    private Long productId;
    @JsonProperty("product_code")
    private String productCode;
    @JsonProperty("product_name")
    private String productName;
    @JsonProperty("product_image")
    private String productImage;
    @JsonProperty("product_price")
    private Double productPrice;
    private Integer quantity;
    private Double subtotal;
}