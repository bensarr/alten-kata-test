package com.example.back.product.dto.response;

import com.example.back.product.model.InventoryStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Product responses.
 * This class is used to send data to clients without exposing the internal entity structure.
 * Note: The id field is intentionally excluded from the response.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {

    private String code;
    private String name;
    private String description;
    private String image;
    private String category;
    private Double price;
    private Integer quantity;
    @JsonProperty("internal_reference")
    private String internalReference;
    @JsonProperty("shell_id")
    private Long shellId;
    @JsonProperty("inventory_status")
    private InventoryStatus inventoryStatus;
    private Integer rating;
    @JsonProperty("created_at")
    private Long createdAt;
    @JsonProperty("updated_at")
    private Long updatedAt;
}