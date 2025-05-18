package com.example.back.product.controller;

import com.example.back.common.dto.ApiDataResponse;
import com.example.back.product.dto.request.ProductCreateRequestDTO;
import com.example.back.product.dto.response.ProductResponseDTO;
import com.example.back.product.dto.request.ProductUpdateRequestDTO;
import com.example.back.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for product management endpoints.
 * This controller handles product CRUD operations.
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductController implements ProductApi {

    private final ProductService productService;

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<ApiDataResponse<ProductResponseDTO>> createProduct(@Valid ProductCreateRequestDTO createDTO) {
        log.debug("REST request to create a new product: {}", createDTO);
        ProductResponseDTO createdProduct = productService.createProduct(createDTO);

        ApiDataResponse<ProductResponseDTO> response = new ApiDataResponse<>(
                true,
                "Product created successfully",
                createdProduct
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        log.debug("REST request to get all products");
        List<ProductResponseDTO> products = productService.getAllProducts();

        return ResponseEntity.ok(products);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
        log.debug("REST request to get product by id: {}", id);
        ProductResponseDTO product = productService.getProductById(id);

        return ResponseEntity.ok(product);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<ApiDataResponse<ProductResponseDTO>> updateProduct(
            @PathVariable Long id,
            @Valid ProductUpdateRequestDTO updateDTO) {
        log.debug("REST request to update product with id: {}", id);
        ProductResponseDTO updatedProduct = productService.updateProduct(id, updateDTO);

        ApiDataResponse<ProductResponseDTO> response = new ApiDataResponse<>(
                true,
                "Product updated successfully",
                updatedProduct
        );

        return ResponseEntity.ok(response);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<ApiDataResponse<Void>> deleteProduct(@PathVariable Long id) {
        log.debug("REST request to delete product with id: {}", id);
        productService.deleteProduct(id);

        ApiDataResponse<Void> response = new ApiDataResponse<>(
                true,
                "Product deleted successfully",
                null
        );

        return ResponseEntity.ok(response);
    }
}