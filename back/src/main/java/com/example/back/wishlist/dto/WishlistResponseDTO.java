package com.example.back.wishlist.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object for wishlist responses.
 * This class is used to send wishlist data to clients.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WishlistResponseDTO {
    private Long id;
    private Long userId;
    private List<WishlistItemDTO> items = new ArrayList<>();
    private Integer totalItems;
    private Long createdAt;
    private Long updatedAt;
}