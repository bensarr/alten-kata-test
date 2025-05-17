package com.example.back.product.service;

import com.example.back.common.exception.ResourceNotFoundException;
import com.example.back.common.exception.ValidationException;
import com.example.back.product.dto.response.ProductResponseDTO;
import com.example.back.product.dto.request.ProductCreateRequestDTO;
import com.example.back.product.dto.request.ProductUpdateRequestDTO;
import com.example.back.product.mapper.ProductMapper;
import com.example.back.product.model.Product;
import com.example.back.product.properties.ProductMessageProperties;
import com.example.back.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductMessageProperties properties;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getAllProducts() {
        log.debug("Retrieving all products");
        List<Product> products = productRepository.findAll();
        return productMapper.toResponseDTOList(products);
    }

    @Transactional(readOnly = true)
    public ProductResponseDTO getProductById(Long id) {
        log.debug("Retrieving product with id: {}", id);
        Objects.requireNonNull(id, properties.getIdNullError());
        return productMapper.toResponseDTO(findProductById(id));
    }


    /**
     * Generates a unique product code.
     * 
     * @return a unique product code
     */
    private String generateUniqueCode() {
        log.debug("Generating unique product code");
        String code;
        do {
            // Generate a code based on UUID to ensure uniqueness
            String uuid = UUID.randomUUID().toString();
            // Ensure the UUID is long enough before taking a substring
            if (uuid.length() >= properties.getUuidMinLength()) {
                code = properties.getCodePrefix() + uuid.substring(0, properties.getCodeLength()).toUpperCase();
            } else {
                // Fallback if UUID is somehow shorter than expected (extremely unlikely)
                code = properties.getCodePrefix() + uuid.toUpperCase();
            }
            log.trace("Generated candidate code: {}", code);
        } while (productRepository.existsByCode(code));

        log.debug("Unique product code generated: {}", code);
        return code;
    }

    /**
     * Creates a new product.
     * Note: Product code is generated automatically and cannot be modified.
     * 
     * @param createDTO the product create DTO
     * @return the created product as a response DTO
     * @throws ValidationException if validation fails
     * @throws NullPointerException if createDTO is null
     */
    @Transactional
    public ProductResponseDTO createProduct(ProductCreateRequestDTO createDTO) {
        log.debug("Creating new product");
        // Validate request
        validateCreateRequest(createDTO);

        // Create product entity from DTO
        Product product = productMapper.toEntity(createDTO);

        // Generate and set a unique code
        String uniqueCode = generateUniqueCode();
        product.setCode(uniqueCode);

        log.debug("Saving new product with code: {}", uniqueCode);
        Product savedProduct = productRepository.save(product);
        log.info("Product created successfully with ID: {} and code: {}", savedProduct.getId(), savedProduct.getCode());
        return productMapper.toResponseDTO(savedProduct);
    }


    /**
     * Validates a product create DTO.
     * Note: Product code is generated automatically and is not validated here.
     * 
     * @param createDTO the product create DTO to validate
     * @throws ValidationException if validation fails
     * @throws NullPointerException if createDTO is null
     */
    private void validateCreateRequest(ProductCreateRequestDTO createDTO) {
        log.debug("Validating product create request");
        Objects.requireNonNull(createDTO, properties.getCreateNullError());

        validateName(createDTO.getName(), true);
        validateNonNegativeValue(createDTO.getPrice(), "price");
        validateNonNegativeValue(createDTO.getQuantity(), "quantity");

        log.debug("Product create request validation successful");
        // Additional validations specific to product creation can be added here
    }

    /**
     * Validates a product update DTO.
     * Note: Product code cannot be modified as it is generated automatically.
     * 
     * @param updateDTO the product update DTO to validate
     * @throws ValidationException if validation fails
     * @throws NullPointerException if updateDTO is null
     */
    private void validateUpdateRequest(ProductUpdateRequestDTO updateDTO) {
        log.debug("Validating product update request");
        Objects.requireNonNull(updateDTO, properties.getUpdateNullError());

        // Name can be null for updates, but if provided, it cannot be empty
        if (updateDTO.getName() != null) {
            validateName(updateDTO.getName(), false);
        }

        validateNonNegativeValue(updateDTO.getPrice(), "price");
        validateNonNegativeValue(updateDTO.getQuantity(), "quantity");

        log.debug("Product update request validation successful");
        // Additional validations specific to product updates can be added here
    }

    /**
     * Validates that a name is not empty.
     *
     * @param name the name to validate
     * @param requireNonNull whether the name is required to be non-null
     * @throws ValidationException if validation fails
     */
    private void validateName(String name, boolean requireNonNull) {
        log.debug("Validating product name: {}, requireNonNull: {}", name, requireNonNull);
        if (requireNonNull && name == null) {
            log.warn("Product name validation failed: name is null but required");
            throw new ValidationException(properties.getNameNullError());
        }

        if (name != null && name.trim().isEmpty()) {
            log.warn("Product name validation failed: name is empty");
            throw new ValidationException(properties.getNameEmptyError());
        }
    }

    /**
     * Validates that a numeric value is not negative.
     *
     * @param <T> the type of the numeric value
     * @param value the value to validate
     * @param fieldName the name of the field being validated
     * @throws ValidationException if validation fails
     */
    private <T extends Number> void validateNonNegativeValue(T value, String fieldName) {
        log.debug("Validating non-negative value for field: {}, value: {}", fieldName, value);
        // Skip validation if value is null (null values are handled by bean validation if required)
        if (value == null) {
            return;
        }

        if (value.doubleValue() < properties.getMinValue()) {
            log.warn("Validation failed: {} is negative: {}", fieldName, value);
            throw new ValidationException(String.format(properties.getNegativeValueError(), fieldName));
        }
    }


    /**
     * Updates a product.
     * Note: Product code cannot be modified as it is generated automatically.
     * 
     * @param id the product ID
     * @param updateDTO the product update DTO
     * @return the updated product as a response DTO
     * @throws ValidationException if validation fails
     * @throws ResourceNotFoundException if the product is not found
     * @throws NullPointerException if id or updateDTO is null
     */
    @Transactional
    public ProductResponseDTO updateProduct(Long id, ProductUpdateRequestDTO updateDTO) {
        log.debug("Updating product with id: {}", id);
        Objects.requireNonNull(id, properties.getIdNullError());
        Objects.requireNonNull(updateDTO, properties.getUpdateNullError());

        // Validate request
        validateUpdateRequest(updateDTO);

        // Find the product
        Product product = findProductById(id);
        log.debug("Found product to update: {}", product.getCode());

        // Update fields using mapper (code will not be updated as it's ignored in the mapper)
        productMapper.updateEntityFromDTO(product, updateDTO);

        // Save the updated product
        log.debug("Saving updated product with code: {}", product.getCode());
        Product updatedProduct = productRepository.save(product);
        log.info("Product updated successfully with ID: {} and code: {}", updatedProduct.getId(), updatedProduct.getCode());

        return productMapper.toResponseDTO(updatedProduct);
    }

    /**
     * Deletes a product.
     * 
     * @param id the product ID
     * @throws ResourceNotFoundException if the product is not found
     * @throws NullPointerException if id is null
     */
    @Transactional
    public void deleteProduct(Long id) {
        log.debug("Deleting product with id: {}", id);
        Objects.requireNonNull(id, properties.getIdNullError());

        Product product = findProductById(id);
        log.debug("Found product to delete: {}", product.getCode());

        productRepository.delete(product);
        log.info("Product deleted successfully with ID: {} and code: {}", id, product.getCode());
    }

    /**
     * Find a product by ID.
     *
     * @param id the product ID
     * @return the product entity
     * @throws ResourceNotFoundException if the product is not found
     * @throws NullPointerException if id is null
     */
    public Product findProductById(Long id) {
        Objects.requireNonNull(id, properties.getIdNullError());
        log.debug("Finding product with id: {}", id);
        return productRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Product not found with id: {}", id);
                    return new ResourceNotFoundException("Product", "id", id);
                });
    }
}
