package com.example.back.cart.service;

import com.example.back.auth.model.User;
import com.example.back.auth.service.UserService;
import com.example.back.cart.dto.request.AddToCartRequestDTO;
import com.example.back.cart.dto.response.CartResponseDTO;
import com.example.back.cart.dto.request.UpdateCartItemRequestDTO;
import com.example.back.cart.mapper.CartMapper;
import com.example.back.cart.model.CartItem;
import com.example.back.cart.model.ShoppingCart;
import com.example.back.cart.properties.CartMessageProperties;
import com.example.back.cart.repository.CartItemRepository;
import com.example.back.cart.repository.ShoppingCartRepository;
import com.example.back.common.exception.ResourceNotFoundException;
import com.example.back.common.exception.ValidationException;
import com.example.back.product.model.InventoryStatus;
import com.example.back.product.model.Product;
import com.example.back.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

/**
 * Service class for shopping cart management.
 * This class provides methods for managing shopping carts and cart items.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final ShoppingCartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductService productService;
    private final UserService userService;
    private final CartMapper cartMapper;
    private final CartMessageProperties properties;

    /**
     * Get the current user's shopping cart.
     * If the cart doesn't exist, it will be created.
     *
     * @return the shopping cart response DTO
     */
    @Transactional(readOnly = true)
    public CartResponseDTO getCurrentUserCart() {
        User user = getCurrentUser();
        ShoppingCart cart = getOrCreateCart(user);
        return cartMapper.toCartResponseDTO(cart);
    }

    /**
     * Find a product by ID.
     *
     * @param productId the product ID
     * @return the product
     * @throws ResourceNotFoundException if the product is not found
     */
    private Product findProductById(Long productId) {
        return productService.findProductById(productId);
    }

    /**
     * Find a cart item by cart and product.
     *
     * @param cart the shopping cart
     * @param product the product
     * @return the cart item
     * @throws ResourceNotFoundException if the cart item is not found
     */
    private CartItem findCartItemByCartAndProduct(ShoppingCart cart, Product product) {
        return cartItemRepository.findByCartAndProduct(cart, product)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item", "productId", product.getId()));
    }

    /**
     * Add a product to the current user's cart.
     *
     * @param addToCartDTO the add to cart DTO
     * @return the updated cart response DTO
     */
    @Transactional
    public CartResponseDTO addToCart(AddToCartRequestDTO addToCartDTO) {
        validateAddToCartRequest(addToCartDTO);

        User user = getCurrentUser();
        ShoppingCart cart = getOrCreateCart(user);

        Product product = findProductById(addToCartDTO.getProductId());

        // Check if the product is in stock
        if (product.getInventoryStatus() == InventoryStatus.OUTOFSTOCK) {
            throw new ValidationException(properties.getProductOutOfStock());
        }

        // Check if the requested quantity is available
        validateStockAvailability(product, addToCartDTO.getQuantity());

        // Check if the product is already in the cart
        Optional<CartItem> existingItem = cartItemRepository.findByCartAndProduct(cart, product);

        if (existingItem.isPresent()) {
            // Update quantity
            CartItem item = existingItem.get();
            int newQuantity = item.getQuantity() + addToCartDTO.getQuantity();

            // Check if the new quantity is available
            validateStockAvailability(product, newQuantity);

            item.setQuantity(newQuantity);
            cartItemRepository.save(item);
        } else {
            // Create new cart item
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(addToCartDTO.getQuantity());
            cartItemRepository.save(newItem);
            cart.getItems().add(newItem);
        }

        return cartMapper.toCartResponseDTO(cart);
    }

    /**
     * Update the quantity of a cart item.
     *
     * @param productId the product ID
     * @param updateCartItemDTO the update cart item DTO
     * @return the updated cart response DTO
     */
    @Transactional
    public CartResponseDTO updateCartItem(Long productId, UpdateCartItemRequestDTO updateCartItemDTO) {
        validateUpdateCartItemRequest(updateCartItemDTO);

        User user = getCurrentUser();
        ShoppingCart cart = getOrCreateCart(user);

        Product product = findProductById(productId);
        CartItem cartItem = findCartItemByCartAndProduct(cart, product);

        // Check if the requested quantity is available
        validateStockAvailability(product, updateCartItemDTO.getQuantity());

        cartItem.setQuantity(updateCartItemDTO.getQuantity());
        cartItemRepository.save(cartItem);

        return cartMapper.toCartResponseDTO(cart);
    }

    /**
     * Remove a product from the cart.
     *
     * @param productId the product ID
     * @return the updated cart response DTO
     */
    @Transactional
    public CartResponseDTO removeFromCart(Long productId) {
        User user = getCurrentUser();
        ShoppingCart cart = getOrCreateCart(user);

        Product product = findProductById(productId);
        CartItem cartItem = findCartItemByCartAndProduct(cart, product);

        cartItemRepository.delete(cartItem);
        cart.getItems().remove(cartItem);

        return cartMapper.toCartResponseDTO(cart);
    }

    /**
     * Clear the current user's cart.
     *
     * @return the empty cart response DTO
     */
    @Transactional
    public CartResponseDTO clearCart() {
        User user = getCurrentUser();
        ShoppingCart cart = getOrCreateCart(user);

        cartItemRepository.deleteByCart(cart);
        cart.getItems().clear();

        return cartMapper.toCartResponseDTO(cart);
    }

    /**
     * Get the current authenticated user.
     *
     * @return the current user
     * @throws ResourceNotFoundException if the user is not found or not authenticated
     */
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            log.error(properties.getNoAuthentication());
            throw new ResourceNotFoundException("User", "authentication", "null");
        }

        String email = authentication.getName();
        log.debug("Getting user for email: {}", email);

        return userService.findUserByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
    }

    /**
     * Get the user's shopping cart or create a new one if it doesn't exist.
     *
     * @param user the user
     * @return the shopping cart
     */
    private ShoppingCart getOrCreateCart(User user) {
        Objects.requireNonNull(user, properties.getUserNullError());

        return cartRepository.findByUser(user)
                .orElseGet(() -> {
                    ShoppingCart newCart = new ShoppingCart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });
    }

    /**
     * Validate an add to cart request.
     *
     * @param addToCartDTO the add to cart DTO
     * @throws ValidationException if validation fails
     */
    private void validateAddToCartRequest(AddToCartRequestDTO addToCartDTO) {
        Objects.requireNonNull(addToCartDTO, properties.getCartCreateNullError());
        Objects.requireNonNull(addToCartDTO.getProductId(), properties.getProductNullError());

        if (addToCartDTO.getQuantity() == null || addToCartDTO.getQuantity() < properties.getMinQuantity()) {
            throw new ValidationException(properties.getQuantityMustBePositive());
        }
    }

    /**
     * Validate an update cart item request.
     *
     * @param updateCartItemDTO the update cart item DTO
     * @throws ValidationException if validation fails
     */
    private void validateUpdateCartItemRequest(UpdateCartItemRequestDTO updateCartItemDTO) {
        Objects.requireNonNull(updateCartItemDTO, properties.getCartUpdateNullError());

        if (updateCartItemDTO.getQuantity() == null || updateCartItemDTO.getQuantity() < properties.getMinQuantity()) {
            throw new ValidationException(properties.getQuantityMustBePositive());
        }
    }

    /**
     * Validate that there is enough stock available for the requested quantity.
     *
     * @param product the product
     * @param requestedQuantity the requested quantity
     * @throws ValidationException if there is not enough stock available
     */
    private void validateStockAvailability(Product product, int requestedQuantity) {
        Objects.requireNonNull(product, properties.getProductNullError());

        if (product.getQuantity() < requestedQuantity) {
            throw new ValidationException(String.format(properties.getNotEnoughStock(), product.getQuantity()));
        }
    }
}
