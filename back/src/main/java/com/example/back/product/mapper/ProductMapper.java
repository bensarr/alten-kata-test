package com.example.back.product.mapper;

import com.example.back.product.dto.request.ProductCreateRequestDTO;
import com.example.back.product.dto.response.ProductResponseDTO;
import com.example.back.product.dto.request.ProductUpdateRequestDTO;
import com.example.back.product.model.Product;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Mapper class to convert between Product entity and DTOs.
 */
@Component
public class ProductMapper {

    /**
     * Converts a Product entity to a ProductResponseDTO.
     *
     * @param product the Product entity
     * @return the ProductResponseDTO
     */
    public ProductResponseDTO toResponseDTO(Product product) {
        if (product == null) {
            return null;
        }

        return new ProductResponseDTO(
            product.getCode(),
            product.getName(),
            product.getDescription(),
            product.getImage(),
            product.getCategory(),
            product.getPrice(),
            product.getQuantity(),
            product.getInternalReference(),
            product.getShellId(),
            product.getInventoryStatus(),
            product.getRating(),
            product.getCreatedAt(),
            product.getUpdatedAt()
        );
    }

    /**
     * Converts a list of Product entities to a list of ProductResponseDTOs.
     *
     * @param products the list of Product entities
     * @return the list of ProductResponseDTOs
     */
    public List<ProductResponseDTO> toResponseDTOList(List<Product> products) {
        if (products == null) {
            return Collections.emptyList();
        }

        return products.stream()
            .map(this::toResponseDTO)
            .toList();
    }


    /**
     * Converts a ProductCreateDTO to a Product entity.
     * Note: This does not set the id, code, createdAt, or updatedAt fields.
     * The code must be set separately as it is generated automatically.
     *
     * @param createDTO the ProductCreateDTO
     * @return the Product entity
     */
    public Product toEntity(ProductCreateRequestDTO createDTO) {
        if (createDTO == null) {
            return null;
        }

        Product product = new Product();
        // Code is not set here as it is generated in the service
        product.setName(createDTO.getName());
        product.setDescription(createDTO.getDescription());
        product.setImage(createDTO.getImage());
        product.setCategory(createDTO.getCategory());
        product.setPrice(createDTO.getPrice());
        product.setQuantity(createDTO.getQuantity());
        product.setInternalReference(createDTO.getInternalReference());
        product.setShellId(createDTO.getShellId());
        product.setInventoryStatus(createDTO.getInventoryStatus());
        product.setRating(createDTO.getRating());
        return product;
    }

    /**
     * Updates a Product entity with data from a ProductUpdateDTO.
     * Only non-null fields in the DTO will be used to update the entity.
     * Note: The code field cannot be modified as it is generated automatically.
     *
     * @param product the Product entity to update
     * @param updateDTO the ProductUpdateDTO with the new data
     */
    public void updateEntityFromDTO(Product product, ProductUpdateRequestDTO updateDTO) {
        if (product == null || updateDTO == null) {
            return;
        }

        // Code is not updated as it cannot be modified

        if (updateDTO.getName() != null) {
            product.setName(updateDTO.getName());
        }

        if (updateDTO.getDescription() != null) {
            product.setDescription(updateDTO.getDescription());
        }

        if (updateDTO.getImage() != null) {
            product.setImage(updateDTO.getImage());
        }

        if (updateDTO.getCategory() != null) {
            product.setCategory(updateDTO.getCategory());
        }

        if (updateDTO.getPrice() != null) {
            product.setPrice(updateDTO.getPrice());
        }

        if (updateDTO.getQuantity() != null) {
            product.setQuantity(updateDTO.getQuantity());
        }

        if (updateDTO.getInternalReference() != null) {
            product.setInternalReference(updateDTO.getInternalReference());
        }

        if (updateDTO.getShellId() != null) {
            product.setShellId(updateDTO.getShellId());
        }

        if (updateDTO.getInventoryStatus() != null) {
            product.setInventoryStatus(updateDTO.getInventoryStatus());
        }

        if (updateDTO.getRating() != null) {
            product.setRating(updateDTO.getRating());
        }
    }
}