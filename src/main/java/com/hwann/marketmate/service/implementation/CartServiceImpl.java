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
        Cart cart = cartRepository.findById(user.getId())
                .orElseGet(() -> {
                    Cart newCart = Cart.builder().user(user).build();
                    cartRepository.save(newCart); // 새로 생성된 카트를 저장
                    return newCart;
                });

        CartItem cartItem = CartItem.builder()
                .cart(cart)
                .product(product)
                .quantity(1)
                .build();

        cartItemRepository.save(cartItem); // 카트 아이템 저장
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
