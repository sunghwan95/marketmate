package com.hwann.wishlistService.service;

import com.hwann.wishlistService.dto.WishlistItemDto;
import com.hwann.wishlistService.entity.Product;
import com.hwann.wishlistService.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

public interface WishlistService {
    Set<WishlistItemDto> getWishlistItemsForUser(User user);

    void addItemToWishlist(User user, Long productId);

    void removeItemFromWishlist(User user, Long productId);

    void deleteWishlist(User user);

    Optional<Product> findWishlistItemById(Long wishlistId, Long productId);

    boolean isWishlistEmpty(User user);

    Optional<Product> findProductById(Long productId);
}
