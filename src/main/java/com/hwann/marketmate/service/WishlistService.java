package com.hwann.marketmate.service;

import com.hwann.marketmate.dto.WishlistDto;
import com.hwann.marketmate.dto.WishlistItemDto;

public interface WishlistService {
    void addToWishlist(WishlistDto wishlistDto, WishlistItemDto wishlistItemDto);

    void removeFromWishlist(Long wishlistItemId);

    void addToCart(Long wishlistItemId);
}
