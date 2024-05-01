package com.hwann.cartService.service.implementation;

import com.hwann.cartService.entity.Cart;
import com.hwann.cartService.entity.CartItem;
import com.hwann.cartService.entity.Product;
import com.hwann.cartService.entity.User;
import com.hwann.cartService.repository.CartItemRepository;
import com.hwann.cartService.repository.CartRepository;
import com.hwann.cartService.service.CartService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public Set<CartItem> getCartItemsForUser(User user) {
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException("Wishlist not found for user"));

        return cart.getItems();
    }

    @Override
    public void addItemToCart(User user, Product product) {
        // 유저의 카트를 조회하거나 없을 경우 새로 생성합니다.
        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = Cart.builder()
                            .user(user)
                            .items(new HashSet<>())
                            .build();
                    cartRepository.save(newCart);
                    return newCart;
                });

        Optional<CartItem> existingCartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getProductId().equals(product.getProductId()))
                .findFirst();

        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            cartItemRepository.save(cartItem);
        } else {
            CartItem newCartItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(1)
                    .build();
            cartItemRepository.save(newCartItem);
        }
    }

    @Override
    public void removeItemFromCart(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    @Override
    public void updateCartItemQuantity(Long cartItemId, int quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new IllegalArgumentException("Cart item not found"));
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
    }
}