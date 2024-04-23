package com.hwann.marketmate.service;

import org.springframework.security.core.Authentication;

public interface WishlistCartFacade {
    void transferItemToCart(Authentication authentication, Long wishlistItemId);
}
