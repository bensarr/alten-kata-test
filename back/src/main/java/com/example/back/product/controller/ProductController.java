package com.example.back.product.controller;

import com.example.back.common.dto.ApiDataResponse;
import com.example.back.product.dto.request.ProductCreateRequestDTO;
import com.example.back.product.dto.response.ProductResponseDTO;
import com.example.back.product.dto.request.ProductUpdateRequestDTO;
import com.example.back.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    
    @PostMapping
    public ResponseEntity<ApiDataResponse<ProductResponseDTO>> createProduct(@Valid @RequestBody ProductCreateRequestDTO createDTO) {
        ProductResponseDTO createdProduct = productService.createProduct(createDTO);
        ApiDataResponse<ProductResponseDTO> response = new ApiDataResponse<>(
                true,
                "Product created successfully",
                createdProduct
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<ProductResponseDTO> products = productService.getAllProducts();

        return ResponseEntity.ok(products);
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
        ProductResponseDTO product = productService.getProductById(id);
        
        return ResponseEntity.ok(product);
    }

    
    @PatchMapping("/{id}")
    public ResponseEntity<ApiDataResponse<ProductResponseDTO>> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductUpdateRequestDTO updateDTO) {
        ProductResponseDTO updatedProduct = productService.updateProduct(id, updateDTO);

        ApiDataResponse<ProductResponseDTO> response = new ApiDataResponse<>(
            true,
            "Product updated successfully",
            updatedProduct
        );

        return ResponseEntity.ok(response);
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiDataResponse<Void>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        
        ApiDataResponse<Void> response = new ApiDataResponse<>(
            true,
            "Product deleted successfully",
            null
        );
        
        return ResponseEntity.ok(response);
    }
}