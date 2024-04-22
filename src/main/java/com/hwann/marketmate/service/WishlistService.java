package com.hwann.marketmate.service;

import com.hwann.marketmate.dto.WishlistDto;
import com.hwann.marketmate.dto.WishlistItemDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface WishlistService {
    void addToWishlist(WishlistDto wishlistDto, WishlistItemDto wishlistItemDto);

    void removeFromWishlist(Long wishlistItemId);

    void addToCart(Long wishlistItemId);

    List<WishlistItemDto> getUserWishlistItems(Authentication authentication) throws Exception;
}
