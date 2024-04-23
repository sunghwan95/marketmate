package com.hwann.marketmate.service;

import com.hwann.marketmate.dto.CartItemDto;
import com.hwann.marketmate.entity.Product;
import com.hwann.marketmate.entity.User;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Set;

public interface CartService {
    Set<CartItemDto> getCartItemsForUser(User user);

    void addItemToCart(User user, Product product);

    void removeItemFromCart(Long cartItemId);

    void updateCartItemQuantity(Long cartItemId, int quantity);
}