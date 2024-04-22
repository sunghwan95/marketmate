package com.hwann.marketmate.repository;

import com.hwann.marketmate.entity.User;
import com.hwann.marketmate.entity.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {
    List<WishlistItem> findByUser(User user);
    List<WishlistItem> findByWishlistId(Long wishlistId);
}
