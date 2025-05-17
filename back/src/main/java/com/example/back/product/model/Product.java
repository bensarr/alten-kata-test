package com.example.back.product.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product code is required")
    @Size(min = 3, max = 50, message = "Product code must be between 3 and 50 characters")
    @Column(nullable = false, unique = true)
    private String code;

    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters")
    @Column(nullable = false)
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

    @Enumerated(EnumType.STRING)
    private InventoryStatus inventoryStatus;

    @Min(value = 0, message = "Rating cannot be negative")
    @Max(value = 5, message = "Rating cannot be greater than 5")
    private Integer rating;

    private Long createdAt;

    private Long updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now().toEpochMilli();
        updatedAt = Instant.now().toEpochMilli();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now().toEpochMilli();
    }
}