package com.example.back.product.dto.request;

import com.example.back.product.model.InventoryStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Product update requests.
 * This class is used to receive data from clients for updating existing products.
 * All fields are optional to allow partial updates.
 * Note: Product code cannot be modified as it is generated automatically.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateRequestDTO {

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
    private String internalReference;

    private Long shellId;

    private InventoryStatus inventoryStatus;

    @Min(value = 0, message = "Rating cannot be negative")
    @Max(value = 5, message = "Rating cannot be greater than 5")
    private Integer rating;
}