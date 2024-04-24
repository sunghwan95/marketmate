package com.hwann.marketmate.service.implementation;

import com.hwann.marketmate.dto.CartItemDto;
import com.hwann.marketmate.entity.*;
import com.hwann.marketmate.repository.CartItemRepository;
import com.hwann.marketmate.repository.CartRepository;
import com.hwann.marketmate.service.CartService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public Set<CartItemDto> getCartItemsForUser(User user) {
        Cart cart = cartRepository.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Wishlist not found for user"));

        return cart.getItems().stream()
                .map(this::convertToDto)
                .collect(Collectors.toSet());
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
                .filter(item -> item.getProduct().getId().equals(product.getId()))
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

    private CartItemDto convertToDto(CartItem item) {

        CartItemDto dto = new CartItemDto();
        dto.setProductId(item.getProduct().getId());
        dto.setQuantity(item.getQuantity());

        return dto;
    }
}
