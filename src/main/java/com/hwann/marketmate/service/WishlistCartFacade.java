package com.hwann.marketmate.service;

import com.hwann.marketmate.entity.User;

public interface WishlistCartFacade {
    void transferItemToCart(User user, Long wishlistItemId);
}
