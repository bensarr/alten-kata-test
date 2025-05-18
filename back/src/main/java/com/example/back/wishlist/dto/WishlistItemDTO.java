package com.example.back.wishlist.dto;

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
    private Long productId;
    private String productCode;
    private String productName;
    private String productImage;
    private Double productPrice;
    private String productCategory;
    private Long addedAt;
}