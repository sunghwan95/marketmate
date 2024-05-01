package com.hwann.wishlistService.service.implementation;

import com.hwann.wishlistService.entity.Product;
import com.hwann.wishlistService.entity.User;
import com.hwann.wishlistService.service.WishlistCartFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WishlistCartFacadeImpl implements WishlistCartFacade {
    private final WishlistServiceImpl wishlistService;
    private final CartServiceImpl cartService;

    @Override
    @Transactional
    public void transferItemToCart(User user, Long productId) {
        // 아이템을 찾아 카트로 이동
        Product product = wishlistService.findProductById(productId)
                .orElseThrow(() -> new IllegalStateException("Wishlist item not found"));

        cartService.addItemToCart(user, product);
        wishlistService.removeItemFromWishlist(user, productId);
    }
}
