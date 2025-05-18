package com.example.back.cart.model;

import com.example.back.product.model.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Entity class for CartItem.
 * This class represents an item in a shopping cart.
 */
@Entity
@Table(name = "cart_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Cart is required")
    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private ShoppingCart cart;

    @NotNull(message = "Product is required")
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    @Column(nullable = false)
    private Integer quantity;

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