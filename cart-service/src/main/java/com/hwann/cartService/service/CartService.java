package com.hwann.cartService.service;

import com.hwann.cartService.entity.CartItem;
import com.hwann.cartService.entity.Product;
import com.hwann.cartService.entity.User;

import java.util.Set;

public interface CartService {
    Set<CartItem> getCartItemsForUser(User user);

    void addItemToCart(User user, Product product);

    void removeItemFromCart(Long cartItemId);

    void updateCartItemQuantity(Long cartItemId, int quantity);
}