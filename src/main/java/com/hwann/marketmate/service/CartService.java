package com.hwann.marketmate.service;

import com.hwann.marketmate.dto.CartItemDto;

import java.util.List;

public interface CartService {
    void addItemToCart(Long userId, CartItemDto cartItemDto);

    void removeItemFromCart(Long cartItemId);

    void updateCartItemQuantity(Long cartItemId, int quantity);

    void orderCheckedProduct(Long userId, List<Long> cartItemIds);
}