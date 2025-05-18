package com.example.back.wishlist.repository;

import com.example.back.auth.model.User;
import com.example.back.wishlist.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Wishlist entity.
 * This interface provides database operations for Wishlist entity.
 */
@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    
    /**
     * Find a wishlist by user.
     * 
     * @param user the user
     * @return an Optional containing the wishlist if found, or empty if not found
     */
    Optional<Wishlist> findByUser(User user);
}