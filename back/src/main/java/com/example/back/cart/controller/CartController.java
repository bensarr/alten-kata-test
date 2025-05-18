package com.example.back.cart.controller;

import com.example.back.cart.dto.request.AddToCartRequestDTO;
import com.example.back.cart.dto.response.CartResponseDTO;
import com.example.back.cart.dto.request.UpdateCartItemRequestDTO;
import com.example.back.cart.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for shopping cart endpoints.
 * This controller handles shopping cart operations.
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class CartController implements CartApi {

    private final CartService cartService;

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<CartResponseDTO> getCart() {
        log.debug("REST request to get current user's cart");
        return ResponseEntity.ok(cartService.getCurrentUserCart());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<CartResponseDTO> addToCart(@Valid AddToCartRequestDTO addToCartDTO) {
        log.debug("REST request to add product to cart: {}", addToCartDTO);
        return ResponseEntity.ok(cartService.addToCart(addToCartDTO));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<CartResponseDTO> updateCartItem(
            Long productId,
            @Valid UpdateCartItemRequestDTO updateCartItemDTO) {
        log.debug("REST request to update cart item for product id: {}", productId);
        return ResponseEntity.ok(cartService.updateCartItem(productId, updateCartItemDTO));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<CartResponseDTO> removeFromCart(Long productId) {
        log.debug("REST request to remove product from cart with id: {}", productId);
        return ResponseEntity.ok(cartService.removeFromCart(productId));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<CartResponseDTO> clearCart() {
        log.debug("REST request to clear cart");
        return ResponseEntity.ok(cartService.clearCart());
    }
}