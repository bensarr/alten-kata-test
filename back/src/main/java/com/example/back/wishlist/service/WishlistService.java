package com.example.back.wishlist.service;

import com.example.back.auth.model.User;
import com.example.back.auth.service.UserService;
import com.example.back.common.exception.ResourceNotFoundException;
import com.example.back.common.exception.ValidationException;
import com.example.back.product.model.Product;
import com.example.back.product.service.ProductService;
import com.example.back.wishlist.dto.AddToWishlistDTO;
import com.example.back.wishlist.dto.WishlistResponseDTO;
import com.example.back.wishlist.mapper.WishlistMapper;
import com.example.back.wishlist.model.Wishlist;
import com.example.back.wishlist.model.WishlistItem;
import com.example.back.wishlist.properties.WishlistMessageProperties;
import com.example.back.wishlist.repository.WishlistItemRepository;
import com.example.back.wishlist.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service class for wishlist management.
 * This class provides methods for managing wishlists and wishlist items.
 */
@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final WishlistItemRepository wishlistItemRepository;
    private final ProductService productService;
    private final UserService userService;
    private final WishlistMapper wishlistMapper;
    private final WishlistMessageProperties properties;

    /**
     * Get the current user's wishlist.
     * If the wishlist doesn't exist, it will be created.
     *
     * @return the wishlist response DTO
     */
    @Transactional(readOnly = true)
    public WishlistResponseDTO getCurrentUserWishlist() {
        User user = getCurrentUser();
        Wishlist wishlist = getOrCreateWishlist(user);
        return wishlistMapper.toWishlistResponseDTO(wishlist);
    }

    /**
     * Add a product to the current user's wishlist.
     *
     * @param addToWishlistDTO the add to wishlist DTO
     * @return the updated wishlist response DTO
     */
    @Transactional
    public WishlistResponseDTO addToWishlist(AddToWishlistDTO addToWishlistDTO) {
        validateAddToWishlistRequest(addToWishlistDTO);

        User user = getCurrentUser();
        Wishlist wishlist = getOrCreateWishlist(user);

        Product product = productService.findProductById(addToWishlistDTO.getProductId());

        // Check if the product is already in the wishlist
        Optional<WishlistItem> existingItem = wishlistItemRepository.findByWishlistAndProduct(wishlist, product);

        if (existingItem.isEmpty()) {
            // Create new wishlist item
            WishlistItem newItem = new WishlistItem();
            newItem.setWishlist(wishlist);
            newItem.setProduct(product);
            wishlistItemRepository.save(newItem);
            wishlist.getItems().add(newItem);
        }

        return wishlistMapper.toWishlistResponseDTO(wishlist);
    }

    /**
     * Remove a product from the wishlist.
     *
     * @param productId the product ID
     * @return the updated wishlist response DTO
     */
    @Transactional
    public WishlistResponseDTO removeFromWishlist(Long productId) {
        User user = getCurrentUser();
        Wishlist wishlist = getOrCreateWishlist(user);

        Product product = productService.findProductById(productId);

        WishlistItem wishlistItem = wishlistItemRepository.findByWishlistAndProduct(wishlist, product)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist item", "productId", productId));

        wishlistItemRepository.delete(wishlistItem);
        wishlist.getItems().remove(wishlistItem);

        return wishlistMapper.toWishlistResponseDTO(wishlist);
    }

    /**
     * Clear the current user's wishlist.
     *
     * @return the empty wishlist response DTO
     */
    @Transactional
    public WishlistResponseDTO clearWishlist() {
        User user = getCurrentUser();
        Wishlist wishlist = getOrCreateWishlist(user);

        wishlistItemRepository.deleteByWishlist(wishlist);
        wishlist.getItems().clear();

        return wishlistMapper.toWishlistResponseDTO(wishlist);
    }

    /**
     * Get the current authenticated user.
     *
     * @return the current user
     * @throws ResourceNotFoundException if the user is not found
     */
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return userService.findUserByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
    }

    /**
     * Get the user's wishlist or create a new one if it doesn't exist.
     *
     * @param user the user
     * @return the wishlist
     */
    private Wishlist getOrCreateWishlist(User user) {
        return wishlistRepository.findByUser(user)
                .orElseGet(() -> {
                    Wishlist newWishlist = new Wishlist();
                    newWishlist.setUser(user);
                    return wishlistRepository.save(newWishlist);
                });
    }

    /**
     * Validate an add to wishlist request.
     *
     * @param addToWishlistDTO the add to wishlist DTO
     * @throws ValidationException if validation fails
     */
    private void validateAddToWishlistRequest(AddToWishlistDTO addToWishlistDTO) {
        if (addToWishlistDTO == null) {
            throw new ValidationException(properties.getRequestNullError());
        }

        if (addToWishlistDTO.getProductId() == null) {
            throw new ValidationException(properties.getProductIdNullError());
        }
    }
}
