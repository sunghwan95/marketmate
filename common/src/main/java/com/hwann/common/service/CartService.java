package com.hwann.common.service;

import com.hwann.common.entity.CartItem;
import com.hwann.common.entity.Product;
import com.hwann.common.entity.User;

import java.util.Set;

public interface CartService {
    Set<CartItem> getCartItemsForUser(User user);

    void addItemToCart(User user, Product product);

    void removeItemFromCart(Long cartItemId);

    void updateCartItemQuantity(Long cartItemId, int quantity);
}