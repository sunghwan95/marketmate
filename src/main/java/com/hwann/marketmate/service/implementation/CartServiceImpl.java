package com.hwann.marketmate.service.implementation;

import com.hwann.marketmate.entity.Cart;
import com.hwann.marketmate.entity.CartItem;
import com.hwann.marketmate.entity.Product;
import com.hwann.marketmate.entity.User;
import com.hwann.marketmate.repository.CartItemRepository;
import com.hwann.marketmate.repository.CartRepository;
import com.hwann.marketmate.service.CartService;
import com.hwann.marketmate.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderService orderService;

    @Override
    public void addItemToCart(User user, Product product) {
        Cart cart = cartRepository.findByUserId(user.getUserId())
                .orElseGet(() -> Cart.builder().user(user).build());

        CartItem cartItem = CartItem.builder()
                .cart(cart)
                .product(product)
                .quantity(1)
                .build();

        cart.getItems().add(cartItem);
        cartItemRepository.save(cartItem);
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

    @Override
    public void moveItemsToOrderService(Long userId, List<Long> cartItemIds) {
        orderService.createOrderFromCartItems(userId, cartItemIds);
    }
}
