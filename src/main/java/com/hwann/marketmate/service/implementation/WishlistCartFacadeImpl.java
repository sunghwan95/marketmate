package com.hwann.marketmate.service.implementation;

import com.hwann.marketmate.entity.User;
import com.hwann.marketmate.entity.WishlistItem;
import com.hwann.marketmate.service.WishlistCartFacade;
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
    public void transferItemToCart(User user, Long wishlistItemId) {
        WishlistItem wishlistItem = wishlistService.findWishlistItemById(wishlistItemId)
                .orElseThrow(() -> new IllegalStateException("Wishlist item not found"));

        cartService.addItemToCart(user, wishlistItem.getProduct());
        wishlistService.removeItemFromWishlist(wishlistItemId);
    }
}
