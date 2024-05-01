package com.hwann.wishlistService.service;

import com.hwann.wishlistService.entity.User;

public interface WishlistCartFacade {
    void transferItemToCart(User user, Long wishlistItemId);
}
