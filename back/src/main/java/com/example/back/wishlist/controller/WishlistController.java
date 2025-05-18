package com.example.back.wishlist.controller;

import com.example.back.wishlist.dto.AddToWishlistDTO;
import com.example.back.wishlist.dto.WishlistResponseDTO;
import com.example.back.wishlist.service.WishlistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for wishlist endpoints.
 * This controller handles wishlist operations.
 */
@RestController
@RequestMapping("/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    /**
     * Get the current user's wishlist.
     *
     * @return the wishlist
     */
    @GetMapping
    public ResponseEntity<WishlistResponseDTO> getWishlist() {
        return ResponseEntity.ok(wishlistService.getCurrentUserWishlist());
    }

    /**
     * Add a product to the wishlist.
     *
     * @param addToWishlistDTO the add to wishlist DTO
     * @return the updated wishlist
     */
    @PostMapping("/items")
    public ResponseEntity<WishlistResponseDTO> addToWishlist(@Valid @RequestBody AddToWishlistDTO addToWishlistDTO) {
        return ResponseEntity.ok(wishlistService.addToWishlist(addToWishlistDTO));
    }

    /**
     * Remove a product from the wishlist.
     *
     * @param productId the product ID
     * @return the updated wishlist
     */
    @DeleteMapping("/items/{productId}")
    public ResponseEntity<WishlistResponseDTO> removeFromWishlist(@PathVariable Long productId) {
        return ResponseEntity.ok(wishlistService.removeFromWishlist(productId));
    }

    /**
     * Clear the wishlist.
     *
     * @return the empty wishlist
     */
    @DeleteMapping
    public ResponseEntity<WishlistResponseDTO> clearWishlist() {
        return ResponseEntity.ok(wishlistService.clearWishlist());
    }
}