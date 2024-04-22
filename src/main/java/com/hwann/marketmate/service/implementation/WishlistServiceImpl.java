package com.hwann.marketmate.service.implementation;

import com.hwann.marketmate.dto.WishlistDto;
import com.hwann.marketmate.dto.WishlistItemDto;
import com.hwann.marketmate.entity.*;
import com.hwann.marketmate.repository.*;
import com.hwann.marketmate.service.WishlistService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class WishlistServiceImpl implements WishlistService {
    private final WishlistRepository wishlistRepository;
    private final WishlistItemRepository wishlistItemRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public void addToWishlist(@Nullable WishlistDto wishlistDto, WishlistItemDto wishlistItemDto) {
        Product product = productRepository.findById(wishlistItemDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        assert wishlistDto != null;
        Wishlist wishlist = wishlistRepository.findByUserId(wishlistDto.getUserId())
                .orElseThrow(() -> new RuntimeException("Wishlist not found"));
        WishlistItem wishlistItem = new WishlistItem();
        wishlistItem.setProduct(product);
        wishlistItem.setWishlist(wishlist);
        wishlistItemRepository.save(wishlistItem);
    }

    @Override
    public void removeFromWishlist(Long wishlistItemId) {
        wishlistItemRepository.deleteById(wishlistItemId);
    }

    @Override
    public void addToCart(Long wishlistItemId) {
        WishlistItem wishlistItem = wishlistItemRepository.findById(wishlistItemId)
                .orElseThrow(() -> new RuntimeException("Wishlist item not found"));

        User user = wishlistItem.getWishlist().getUser();
        Cart cart = cartRepository.findByUserId(user.getUserId())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return newCart;
                });

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(wishlistItem.getProduct());
        cartItem.setQuantity(1);
        cartItemRepository.save(cartItem);

        wishlistItemRepository.delete(wishlistItem);
    }
}
