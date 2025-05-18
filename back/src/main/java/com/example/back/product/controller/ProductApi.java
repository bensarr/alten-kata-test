package com.example.back.product.controller;

import com.example.back.common.dto.ApiDataResponse;
import com.example.back.common.exception.ResourceNotFoundException;
import com.example.back.common.exception.ValidationException;
import com.example.back.product.dto.request.ProductCreateRequestDTO;
import com.example.back.product.dto.request.ProductUpdateRequestDTO;
import com.example.back.product.dto.response.ProductResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Interface defining product management endpoints with Swagger documentation.
 */
@Tag(name = "Products", description = "API for product management operations")
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/products")
public interface ProductApi {

    /**
     * Endpoint to create a new product.
     *
     * @param createDTO the product creation data
     * @return the created product with success message
     * @throws ValidationException if the input data fails validation
     */
    @Operation(
            summary = "Create a new product",
            description = "Creates a new product with the provided information. Automatically generates a unique product code."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Product successfully created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiDataResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid product information",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiDataResponse.class))
            )
    })
    @PostMapping
    ResponseEntity<ApiDataResponse<ProductResponseDTO>> createProduct(
            @Parameter(description = "Product creation information", required = true)
            @Valid @RequestBody ProductCreateRequestDTO createDTO);

    /**
     * Endpoint to retrieve all products.
     *
     * @return a list of all products
     */
    @Operation(
            summary = "Get all products",
            description = "Retrieves a list of all available products"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Products successfully retrieved",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ProductResponseDTO.class)))
            )
    })
    @GetMapping
    ResponseEntity<List<ProductResponseDTO>> getAllProducts();

    /**
     * Endpoint to retrieve a product by its ID.
     *
     * @param id the product ID
     * @return the product with the specified ID
     * @throws ResourceNotFoundException if the product is not found
     */
    @Operation(
            summary = "Get product by ID",
            description = "Retrieves a specific product based on its ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product successfully retrieved",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found",
                    content = @Content(mediaType = "application/json")
            )
    })
    @GetMapping("/{id}")
    ResponseEntity<ProductResponseDTO> getProductById(
            @Parameter(description = "Product ID", required = true)
            @PathVariable Long id);

    /**
     * Endpoint to update an existing product.
     *
     * @param id the product ID
     * @param updateDTO the product update data
     * @return the updated product with success message
     * @throws ResourceNotFoundException if the product is not found
     * @throws ValidationException if the input data fails validation
     */
    @Operation(
            summary = "Update a product",
            description = "Updates an existing product with the provided information. Product code cannot be modified."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product successfully updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiDataResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid product information",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiDataResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PatchMapping("/{id}")
    ResponseEntity<ApiDataResponse<ProductResponseDTO>> updateProduct(
            @Parameter(description = "Product ID", required = true)
            @PathVariable Long id,
            @Parameter(description = "Product update information", required = true)
            @Valid @RequestBody ProductUpdateRequestDTO updateDTO);

    /**
     * Endpoint to delete a product.
     *
     * @param id the product ID
     * @return a success message
     * @throws ResourceNotFoundException if the product is not found
     */
    @Operation(
            summary = "Delete a product",
            description = "Deletes a product based on its ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product successfully deleted",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiDataResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found",
                    content = @Content(mediaType = "application/json")
            )
    })
    @DeleteMapping("/{id}")
    ResponseEntity<ApiDataResponse<Void>> deleteProduct(
            @Parameter(description = "Product ID", required = true)
            @PathVariable Long id);
}