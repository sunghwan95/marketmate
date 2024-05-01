package com.hwann.common.service;

import com.hwann.common.dto.WishlistItemDto;
import com.hwann.common.entity.Product;
import com.hwann.common.entity.User;
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
