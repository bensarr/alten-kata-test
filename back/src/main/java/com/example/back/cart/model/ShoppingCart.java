package com.example.back.cart.model;

import com.example.back.auth.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity class for ShoppingCart.
 * This class represents a user's shopping cart in the system.
 */
@Entity
@Table(name = "shopping_carts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "User is required")
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull(message = "Items list cannot be null")
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

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