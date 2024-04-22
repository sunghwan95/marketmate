package com.hwann.marketmate.service;

import com.hwann.marketmate.dto.WishlistItemDto;
import com.hwann.marketmate.entity.User;

import java.util.List;

public interface WishlistService {
    public void addToWishlist(Long productId, User user);

    public void removeFromWishlist(Long wishlistItemId);

    public List<WishlistItemDto> getWishlist(User user);
}
