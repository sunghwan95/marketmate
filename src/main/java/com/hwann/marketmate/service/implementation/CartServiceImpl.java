package com.hwann.marketmate.service.implementation;

import com.hwann.marketmate.dto.CartItemDto;
import com.hwann.marketmate.entity.Cart;
import com.hwann.marketmate.entity.CartItem;
import com.hwann.marketmate.entity.Product;
import com.hwann.marketmate.repository.CartItemRepository;
import com.hwann.marketmate.repository.CartRepository;
import com.hwann.marketmate.repository.ProductRepository;
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
    private final ProductRepository productRepository;
    private final OrderService orderService;

    public void addItemToCart(Long userId, CartItemDto cartItemDto) {
        Product product = productRepository.findById(cartItemDto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        Cart cart = cartRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found"));

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(cartItemDto.getQuantity());
        cartItem.setCart(cart);

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
    public void orderCheckedProduct(Long userId, List<Long> cartItemIds) {
        orderService.createOrderFromCartItems(userId, cartItemIds);
    }
}
