package com.example.back.wishlist.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("user_id")
    private Long userId;
    private List<WishlistItemDTO> items = new ArrayList<>();
    @JsonProperty("total_items")
    private Integer totalItems;
    @JsonProperty("created_at")
    private Long createdAt;
    @JsonProperty("updated_at")
    private Long updatedAt;
}