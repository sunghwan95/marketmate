package com.hwann.common.service;

import com.hwann.common.entity.User;

public interface WishlistCartFacade {
    void transferItemToCart(User user, Long wishlistItemId);
}
