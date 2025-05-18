package com.example.back.cart.mapper;

import com.example.back.cart.dto.request.CartItemRequestDTO;
import com.example.back.cart.dto.response.CartResponseDTO;
import com.example.back.cart.model.CartItem;
import com.example.back.cart.model.ShoppingCart;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Mapper class to convert between ShoppingCart/CartItem entities and DTOs.
 */
@Component
public class CartMapper {

    /**
     * Converts a CartItem entity to a CartItemDTO.
     *
     * @param cartItem the CartItem entity
     * @return the CartItemDTO
     */
    public CartItemRequestDTO toCartItemDTO(CartItem cartItem) {
        if (cartItem == null) {
            return null;
        }

        CartItemRequestDTO dto = new CartItemRequestDTO();
        dto.setProductId(cartItem.getProduct().getId());
        dto.setProductCode(cartItem.getProduct().getCode());
        dto.setProductName(cartItem.getProduct().getName());
        dto.setProductImage(cartItem.getProduct().getImage());
        dto.setProductPrice(cartItem.getProduct().getPrice());
        dto.setQuantity(cartItem.getQuantity());
        
        // Calculate subtotal
        if (cartItem.getProduct().getPrice() != null && cartItem.getQuantity() != null) {
            dto.setSubtotal(cartItem.getProduct().getPrice() * cartItem.getQuantity());
        }
        
        return dto;
    }

    /**
     * Converts a list of CartItem entities to a list of CartItemDTOs.
     *
     * @param cartItems the list of CartItem entities
     * @return the list of CartItemDTOs
     */
    public List<CartItemRequestDTO> toCartItemDTOList(List<CartItem> cartItems) {
        if (cartItems == null) {
            return Collections.emptyList();
        }

        return cartItems.stream()
                .map(this::toCartItemDTO)
                .toList();
    }

    /**
     * Converts a ShoppingCart entity to a CartResponseDTO.
     *
     * @param cart the ShoppingCart entity
     * @return the CartResponseDTO
     */
    public CartResponseDTO toCartResponseDTO(ShoppingCart cart) {
        if (cart == null) {
            return null;
        }

        CartResponseDTO dto = new CartResponseDTO();
        dto.setId(cart.getId());
        dto.setUserId(cart.getUser().getId());
        dto.setCreatedAt(cart.getCreatedAt());
        dto.setUpdatedAt(cart.getUpdatedAt());
        
        // Convert cart items
        List<CartItemRequestDTO> itemDTOs = toCartItemDTOList(cart.getItems());
        dto.setItems(itemDTOs);
        
        // Calculate total items and total price
        int totalItems = 0;
        double totalPrice = 0.0;
        
        for (CartItemRequestDTO item : itemDTOs) {
            if (item.getQuantity() != null) {
                totalItems += item.getQuantity();
            }
            
            if (item.getSubtotal() != null) {
                totalPrice += item.getSubtotal();
            }
        }
        
        dto.setTotalItems(totalItems);
        dto.setTotalPrice(totalPrice);
        
        return dto;
    }
}