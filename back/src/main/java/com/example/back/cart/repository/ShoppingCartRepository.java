package com.example.back.cart.repository;

import com.example.back.auth.model.User;
import com.example.back.cart.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for ShoppingCart entity.
 * This interface provides database operations for ShoppingCart entity.
 */
@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    
    /**
     * Find a shopping cart by user.
     * 
     * @param user the user
     * @return an Optional containing the shopping cart if found, or empty if not found
     */
    Optional<ShoppingCart> findByUser(User user);
}