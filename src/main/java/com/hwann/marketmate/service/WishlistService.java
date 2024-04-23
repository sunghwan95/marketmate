package com.hwann.marketmate.service;

import com.hwann.marketmate.dto.WishlistItemDto;
import com.hwann.marketmate.entity.User;
import com.hwann.marketmate.entity.WishlistItem;
import org.springframework.security.core.Authentication;

import java.util.Optional;
import java.util.Set;

public interface WishlistService {
    Set<WishlistItemDto> getWishlistItemsForUser(User user);
    void addItemToWishlist(WishlistItemDto wishlistItemDto, User user);
    void removeItemFromWishlist(Long wishlistItemId);
    Optional<WishlistItem> findWishlistItemById(Long wishlistItemId);
}
