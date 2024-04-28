package com.hwann.marketmate.service;

import com.hwann.marketmate.entity.CartItem;
import com.hwann.marketmate.entity.Product;
import com.hwann.marketmate.entity.User;

import java.util.Set;

public interface CartService {
    Set<CartItem> getCartItemsForUser(User user);

    void addItemToCart(User user, Product product);

    void removeItemFromCart(Long cartItemId);

    void updateCartItemQuantity(Long cartItemId, int quantity);
}