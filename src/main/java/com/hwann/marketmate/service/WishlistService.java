package com.hwann.marketmate.service;

import com.hwann.marketmate.dto.WishlistItemDto;
import com.hwann.marketmate.entity.WishlistItem;
import org.springframework.security.core.Authentication;

import java.util.Optional;
import java.util.Set;

public interface WishlistService {
    Set<WishlistItemDto> getWishlistItemsForUser(Authentication authentication);

    void addItemToWishlist(WishlistItemDto wishlistItemDto, Authentication authentication);

    void removeItemFromWishlist(Long wishlistItemId);

    Optional<WishlistItem> findWishlistItemById(Long wishlistItemId);
}
