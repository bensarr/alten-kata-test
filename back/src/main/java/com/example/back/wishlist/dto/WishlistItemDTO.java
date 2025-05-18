package com.example.back.wishlist.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for wishlist items.
 * This class is used to send wishlist item data to clients.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WishlistItemDTO {
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
    @JsonProperty("product_category")
    private String productCategory;
    @JsonProperty("added_at")
    private Long addedAt;
}