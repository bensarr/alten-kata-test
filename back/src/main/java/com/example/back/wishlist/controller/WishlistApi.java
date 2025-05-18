package com.example.back.wishlist.controller;

import com.example.back.common.exception.ResourceNotFoundException;
import com.example.back.common.exception.ValidationException;
import com.example.back.wishlist.dto.request.AddToWishlistRequestDTO;
import com.example.back.wishlist.dto.response.WishlistResponseDTO;
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
 * Interface defining wishlist management endpoints with Swagger documentation.
 */
@Tag(name = "Wishlist", description = "API for wishlist management operations")
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/wishlist")
public interface WishlistApi {

    /**
     * Endpoint to retrieve the current user's wishlist.
     *
     * @return the current user's wishlist
     */
    @Operation(
            summary = "Get current user's wishlist",
            description = "Retrieves the current authenticated user's wishlist. Creates a new wishlist if it doesn't exist."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Wishlist successfully retrieved",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = WishlistResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Not authenticated",
                    content = @Content(mediaType = "application/json")
            )
    })
    @GetMapping
    ResponseEntity<WishlistResponseDTO> getWishlist();

    /**
     * Endpoint to add a product to the wishlist.
     *
     * @param addToWishlistDTO the add to wishlist request data
     * @return the updated wishlist
     * @throws ValidationException if the input data fails validation
     * @throws ResourceNotFoundException if the product is not found
     */
    @Operation(
            summary = "Add product to wishlist",
            description = "Adds a product to the current user's wishlist. Does nothing if product already exists in wishlist."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product successfully added to wishlist",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = WishlistResponseDTO.class))
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
    ResponseEntity<WishlistResponseDTO> addToWishlist(
            @Parameter(description = "Add to wishlist request data", required = true)
            @Valid @RequestBody AddToWishlistRequestDTO addToWishlistDTO);

    /**
     * Endpoint to remove a product from the wishlist.
     *
     * @param productId the product ID
     * @return the updated wishlist
     * @throws ResourceNotFoundException if the product or wishlist item is not found
     */
    @Operation(
            summary = "Remove product from wishlist",
            description = "Removes a specific product from the current user's wishlist"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product successfully removed from wishlist",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = WishlistResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found or not in wishlist",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Not authenticated",
                    content = @Content(mediaType = "application/json")
            )
    })
    @DeleteMapping("/items/{productId}")
    ResponseEntity<WishlistResponseDTO> removeFromWishlist(
            @Parameter(description = "Product ID", required = true)
            @PathVariable Long productId);

    /**
     * Endpoint to clear the wishlist.
     *
     * @return the empty wishlist
     */
    @Operation(
            summary = "Clear wishlist",
            description = "Removes all items from the current user's wishlist"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Wishlist successfully cleared",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = WishlistResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Not authenticated",
                    content = @Content(mediaType = "application/json")
            )
    })
    @DeleteMapping
    ResponseEntity<WishlistResponseDTO> clearWishlist();
}