package com.example.back.product.dto.request;

import com.example.back.product.model.InventoryStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Product creation requests.
 * This class is used to receive data from clients for creating new products.
 * It enforces mandatory fields for product creation.
 * Note: Product code is generated automatically and cannot be modified.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateRequestDTO {

    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters")
    private String name;

    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private String description;

    @Size(max = 255, message = "Image URL cannot exceed 255 characters")
    private String image;

    @Size(max = 50, message = "Category cannot exceed 50 characters")
    private String category;

    @DecimalMin(value = "0.0", message = "Price cannot be negative")
    private Double price;

    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity;

    @Size(max = 50, message = "Internal reference cannot exceed 50 characters")
    @JsonProperty("internal_reference")
    private String internalReference;

    @JsonProperty("shell_id")
    private Long shellId;

    @JsonProperty("inventory_status")
    private InventoryStatus inventoryStatus;

    @Min(value = 0, message = "Rating cannot be negative")
    @Max(value = 5, message = "Rating cannot be greater than 5")
    private Integer rating;
}