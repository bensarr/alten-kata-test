package com.example.back.cart.controller;

import com.example.back.cart.dto.AddToCartDTO;
import com.example.back.cart.dto.CartResponseDTO;
import com.example.back.cart.dto.UpdateCartItemDTO;
import com.example.back.cart.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for shopping cart endpoints.
 * This controller handles shopping cart operations.
 */
@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    /**
     * Get the current user's shopping cart.
     *
     * @return the shopping cart
     */
    @GetMapping
    public ResponseEntity<CartResponseDTO> getCart() {
        return ResponseEntity.ok(cartService.getCurrentUserCart());
    }

    /**
     * Add a product to the cart.
     *
     * @param addToCartDTO the add to cart DTO
     * @return the updated cart
     */
    @PostMapping("/items")
    public ResponseEntity<CartResponseDTO> addToCart(@Valid @RequestBody AddToCartDTO addToCartDTO) {
        return ResponseEntity.ok(cartService.addToCart(addToCartDTO));
    }

    /**
     * Update the quantity of a cart item.
     *
     * @param productId the product ID
     * @param updateCartItemDTO the update cart item DTO
     * @return the updated cart
     */
    @PatchMapping("/items/{productId}")
    public ResponseEntity<CartResponseDTO> updateCartItem(
            @PathVariable Long productId,
            @Valid @RequestBody UpdateCartItemDTO updateCartItemDTO) {
        return ResponseEntity.ok(cartService.updateCartItem(productId, updateCartItemDTO));
    }

    /**
     * Remove a product from the cart.
     *
     * @param productId the product ID
     * @return the updated cart
     */
    @DeleteMapping("/items/{productId}")
    public ResponseEntity<CartResponseDTO> removeFromCart(@PathVariable Long productId) {
        return ResponseEntity.ok(cartService.removeFromCart(productId));
    }

    /**
     * Clear the cart.
     *
     * @return the empty cart
     */
    @DeleteMapping
    public ResponseEntity<CartResponseDTO> clearCart() {
        return ResponseEntity.ok(cartService.clearCart());
    }
}