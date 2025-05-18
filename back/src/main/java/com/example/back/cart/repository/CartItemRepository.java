package com.example.back.cart.repository;

import com.example.back.cart.model.CartItem;
import com.example.back.cart.model.ShoppingCart;
import com.example.back.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for CartItem entity.
 * This interface provides database operations for CartItem entity.
 */
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    
    /**
     * Find a cart item by cart and product.
     * 
     * @param cart the shopping cart
     * @param product the product
     * @return an Optional containing the cart item if found, or empty if not found
     */
    Optional<CartItem> findByCartAndProduct(ShoppingCart cart, Product product);
    
    /**
     * Delete all cart items for the given cart.
     * 
     * @param cart the shopping cart
     */
    void deleteByCart(ShoppingCart cart);
}