package com.hwann.marketmate.service.implementation;

import com.hwann.marketmate.dto.WishlistItemDto;
import com.hwann.marketmate.entity.*;
import com.hwann.marketmate.repository.*;
import com.hwann.marketmate.service.WishlistService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {
    private final WishlistRepository wishlistRepository;
    private final WishlistItemRepository wishlistItemRepository;
    private final ProductServiceImpl productService;

    @Override
    public Set<WishlistItemDto> getWishlistItemsForUser(User user) {
        Wishlist wishlist = wishlistRepository.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Wishlist not found for user"));

        return wishlist.getItems().stream()
                .map(this::convertToDto)
                .collect(Collectors.toSet());
    }

    @Override
    public void addItemToWishlist(WishlistItemDto wishlistItemDto, User user) {
        Product product = productService.getProductById(wishlistItemDto.getProductId());

        Wishlist wishlist = wishlistRepository.findByUser(user)
                .orElseGet(() -> {
                    Wishlist newWishlist = Wishlist.builder()
                            .user(user)
                            .items(new HashSet<>())
                            .build();
                    wishlistRepository.save(newWishlist);
                    return newWishlist;
                });

        Optional<WishlistItem> existingWishlistItem = wishlist.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst();

        if (existingWishlistItem.isPresent()) {
            throw new IllegalStateException("이미 찜한 물품입니다.");
        } else {
            WishlistItem newWishlistItem = WishlistItem.builder()
                    .wishlist(wishlist)
                    .product(product)
                    .build();
            wishlistItemRepository.save(newWishlistItem); // 새 위시리스트 아이템 저장
            wishlist.getItems().add(newWishlistItem); // 컬렉션에도 추가
            System.out.println("찜물품들 :" + wishlist.getItems());
        }
    }

    @Override
    public void removeItemFromWishlist(Long wishlistItemId) {
        wishlistItemRepository.deleteById(wishlistItemId);
    }

    @Override
    public void deleteWishlist(User user) {
        Wishlist wishlist = wishlistRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Wishlist not found for user"));

        wishlistRepository.delete(wishlist);
    }

    @Override
    public Optional<WishlistItem> findWishlistItemById(Long wishlistItemId) {
        Optional<WishlistItem> item = wishlistItemRepository.findById(wishlistItemId);
        System.out.println("아이템 : " + item);
        return item;
    }

    @Override
    public boolean isWishlistEmpty(User user) {
        Wishlist wishlist = wishlistRepository.findByUser(user)
                .orElseThrow(() -> new IllegalStateException("Wishlist not found for user"));
        return wishlist.getItems().isEmpty();
    }

    private WishlistItemDto convertToDto(WishlistItem item) {
        return new WishlistItemDto(item.getProduct().getId(), item.getProduct().getName());
    }
}
