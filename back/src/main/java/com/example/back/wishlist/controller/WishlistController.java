package com.example.back.wishlist.controller;

import com.example.back.wishlist.dto.request.AddToWishlistRequestDTO;
import com.example.back.wishlist.dto.response.WishlistResponseDTO;
import com.example.back.wishlist.service.WishlistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for wishlist endpoints.
 * This controller handles wishlist operations.
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class WishlistController implements WishlistApi {

    private final WishlistService wishlistService;

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<WishlistResponseDTO> getWishlist() {
        log.debug("REST request to get current user's wishlist");
        return ResponseEntity.ok(wishlistService.getCurrentUserWishlist());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<WishlistResponseDTO> addToWishlist(@Valid AddToWishlistRequestDTO addToWishlistDTO) {
        log.debug("REST request to add product to wishlist: {}", addToWishlistDTO);
        return ResponseEntity.ok(wishlistService.addToWishlist(addToWishlistDTO));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<WishlistResponseDTO> removeFromWishlist(Long productId) {
        log.debug("REST request to remove product from wishlist with id: {}", productId);
        return ResponseEntity.ok(wishlistService.removeFromWishlist(productId));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<WishlistResponseDTO> clearWishlist() {
        log.debug("REST request to clear wishlist");
        return ResponseEntity.ok(wishlistService.clearWishlist());
    }
}