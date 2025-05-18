package com.example.back.wishlist.mapper;

import com.example.back.wishlist.dto.WishlistItemDTO;
import com.example.back.wishlist.dto.WishlistResponseDTO;
import com.example.back.wishlist.model.Wishlist;
import com.example.back.wishlist.model.WishlistItem;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Mapper class to convert between Wishlist/WishlistItem entities and DTOs.
 */
@Component
public class WishlistMapper {

    /**
     * Converts a WishlistItem entity to a WishlistItemDTO.
     *
     * @param wishlistItem the WishlistItem entity
     * @return the WishlistItemDTO
     */
    public WishlistItemDTO toWishlistItemDTO(WishlistItem wishlistItem) {
        if (wishlistItem == null) {
            return null;
        }

        WishlistItemDTO dto = new WishlistItemDTO();
        dto.setProductId(wishlistItem.getProduct().getId());
        dto.setProductCode(wishlistItem.getProduct().getCode());
        dto.setProductName(wishlistItem.getProduct().getName());
        dto.setProductImage(wishlistItem.getProduct().getImage());
        dto.setProductPrice(wishlistItem.getProduct().getPrice());
        dto.setProductCategory(wishlistItem.getProduct().getCategory());
        dto.setAddedAt(wishlistItem.getCreatedAt());
        
        return dto;
    }

    /**
     * Converts a list of WishlistItem entities to a list of WishlistItemDTOs.
     *
     * @param wishlistItems the list of WishlistItem entities
     * @return the list of WishlistItemDTOs
     */
    public List<WishlistItemDTO> toWishlistItemDTOList(List<WishlistItem> wishlistItems) {
        if (wishlistItems == null) {
            return Collections.emptyList();
        }

        return wishlistItems.stream()
                .map(this::toWishlistItemDTO)
                .toList();
    }

    /**
     * Converts a Wishlist entity to a WishlistResponseDTO.
     *
     * @param wishlist the Wishlist entity
     * @return the WishlistResponseDTO
     */
    public WishlistResponseDTO toWishlistResponseDTO(Wishlist wishlist) {
        if (wishlist == null) {
            return null;
        }

        WishlistResponseDTO dto = new WishlistResponseDTO();
        dto.setId(wishlist.getId());
        dto.setUserId(wishlist.getUser().getId());
        dto.setCreatedAt(wishlist.getCreatedAt());
        dto.setUpdatedAt(wishlist.getUpdatedAt());
        
        // Convert wishlist items
        List<WishlistItemDTO> itemDTOs = toWishlistItemDTOList(wishlist.getItems());
        dto.setItems(itemDTOs);
        
        // Set total items
        dto.setTotalItems(itemDTOs.size());
        
        return dto;
    }
}