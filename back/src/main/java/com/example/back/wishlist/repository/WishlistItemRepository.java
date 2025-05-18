package com.example.back.wishlist.repository;

import com.example.back.product.model.Product;
import com.example.back.wishlist.model.Wishlist;
import com.example.back.wishlist.model.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for WishlistItem entity.
 * This interface provides database operations for WishlistItem entity.
 */
@Repository
public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {
    
    /**
     * Find a wishlist item by wishlist and product.
     * 
     * @param wishlist the wishlist
     * @param product the product
     * @return an Optional containing the wishlist item if found, or empty if not found
     */
    Optional<WishlistItem> findByWishlistAndProduct(Wishlist wishlist, Product product);
    
    /**
     * Delete all wishlist items for the given wishlist.
     * 
     * @param wishlist the wishlist
     */
    void deleteByWishlist(Wishlist wishlist);
}