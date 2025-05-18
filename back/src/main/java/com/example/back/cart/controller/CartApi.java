package com.example.back.cart.controller;

import com.example.back.cart.dto.request.AddToCartRequestDTO;
import com.example.back.cart.dto.request.UpdateCartItemRequestDTO;
import com.example.back.cart.dto.response.CartResponseDTO;
import com.example.back.common.exception.ResourceNotFoundException;
import com.example.back.common.exception.ValidationException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Interface defining shopping cart management endpoints with Swagger documentation.
 */
@Tag(name = "Shopping Cart", description = "API for shopping cart management operations")
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/cart")
public interface CartApi {

    /**
     * Endpoint to retrieve the current user's shopping cart.
     *
     * @return the current user's shopping cart
     */
    @Operation(
            summary = "Get current user's shopping cart",
            description = "Retrieves the current authenticated user's shopping cart. Creates a new cart if it doesn't exist."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cart successfully retrieved",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CartResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Not authenticated",
                    content = @Content(mediaType = "application/json")
            )
    })
    @GetMapping
    ResponseEntity<CartResponseDTO> getCart();

    /**
     * Endpoint to add a product to the cart.
     *
     * @param addToCartDTO the add to cart request data
     * @return the updated cart
     * @throws ValidationException if the input data fails validation
     * @throws ResourceNotFoundException if the product is not found
     */
    @Operation(
            summary = "Add product to cart",
            description = "Adds a product to the current user's shopping cart. Updates quantity if product already exists in cart."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product successfully added to cart",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CartResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request data",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Not authenticated",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PostMapping("/items")
    ResponseEntity<CartResponseDTO> addToCart(
            @Parameter(description = "Add to cart request data", required = true)
            @Valid @RequestBody AddToCartRequestDTO addToCartDTO);

    /**
     * Endpoint to update the quantity of a cart item.
     *
     * @param productId the product ID
     * @param updateCartItemDTO the update cart item request data
     * @return the updated cart
     * @throws ValidationException if the input data fails validation
     * @throws ResourceNotFoundException if the product or cart item is not found
     */
    @Operation(
            summary = "Update cart item quantity",
            description = "Updates the quantity of a specific product in the current user's shopping cart"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cart item successfully updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CartResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request data",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found or not in cart",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Not authenticated",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PatchMapping("/items/{productId}")
    ResponseEntity<CartResponseDTO> updateCartItem(
            @Parameter(description = "Product ID", required = true)
            @PathVariable Long productId,
            @Parameter(description = "Update cart item request data", required = true)
            @Valid @RequestBody UpdateCartItemRequestDTO updateCartItemDTO);

    /**
     * Endpoint to remove a product from the cart.
     *
     * @param productId the product ID
     * @return the updated cart
     * @throws ResourceNotFoundException if the product or cart item is not found
     */
    @Operation(
            summary = "Remove product from cart",
            description = "Removes a specific product from the current user's shopping cart"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product successfully removed from cart",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CartResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found or not in cart",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Not authenticated",
                    content = @Content(mediaType = "application/json")
            )
    })
    @DeleteMapping("/items/{productId}")
    ResponseEntity<CartResponseDTO> removeFromCart(
            @Parameter(description = "Product ID", required = true)
            @PathVariable Long productId);

    /**
     * Endpoint to clear the cart.
     *
     * @return the empty cart
     */
    @Operation(
            summary = "Clear cart",
            description = "Removes all items from the current user's shopping cart"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cart successfully cleared",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CartResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Not authenticated",
                    content = @Content(mediaType = "application/json")
            )
    })
    @DeleteMapping
    ResponseEntity<CartResponseDTO> clearCart();
}