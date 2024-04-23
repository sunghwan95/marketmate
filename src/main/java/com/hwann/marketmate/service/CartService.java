package com.hwann.marketmate.service;

import com.hwann.marketmate.dto.CartItemDto;
import com.hwann.marketmate.entity.Product;
import com.hwann.marketmate.entity.User;

import java.util.List;

public interface CartService {
    void addItemToCart(User user, Product product);

    void removeItemFromCart(Long cartItemId);

    void updateCartItemQuantity(Long cartItemId, int quantity);
}