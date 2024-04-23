package com.hwann.marketmate.service.implementation;

import com.hwann.marketmate.entity.User;
import com.hwann.marketmate.entity.WishlistItem;
import com.hwann.marketmate.service.CartService;
import com.hwann.marketmate.service.UserService;
import com.hwann.marketmate.service.WishlistCartFacade;
import com.hwann.marketmate.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WishlistCartFacadeImpl implements WishlistCartFacade {
    private final WishlistService wishlistService;
    private final CartService cartService;
    private final UserService userService;

    @Override
    @Transactional
    public void transferItemToCart(Authentication authentication, Long wishlistItemId) {
        User user = userService.identifyUser(authentication);
        WishlistItem wishlistItem = wishlistService.findWishlistItemById(wishlistItemId)
                .orElseThrow(() -> new IllegalStateException("Wishlist item not found"));

        cartService.addItemToCart(user, wishlistItem.getProduct());
        wishlistService.removeItemFromWishlist(wishlistItemId);
    }
}
