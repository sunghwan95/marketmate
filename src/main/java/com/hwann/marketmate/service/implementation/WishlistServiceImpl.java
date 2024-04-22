package com.hwann.marketmate.service.implementation;

import com.hwann.marketmate.dto.WishlistItemDto;
import com.hwann.marketmate.entity.Product;
import com.hwann.marketmate.entity.User;
import com.hwann.marketmate.entity.WishlistItem;
import com.hwann.marketmate.repository.ProductRepository;
import com.hwann.marketmate.repository.WishlistItemRepository;
import com.hwann.marketmate.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {
    private final WishlistItemRepository wishlistItemRepository;
    private final ProductRepository productRepository;

    @Override
    public void addToWishlist(Long productId, User user) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        WishlistItem wishlistItem = new WishlistItem();
        wishlistItem.setProduct(Optional.ofNullable(product));
        wishlistItem.setUser(user);
        wishlistItemRepository.save(wishlistItem);
    }

    @Override
    public void removeFromWishlist(Long wishlistItemId) {
        wishlistItemRepository.deleteById(wishlistItemId);
    }

    @Override
    public List<WishlistItemDto> getWishlist(User user) {
        List<WishlistItem> wishlistItems = wishlistItemRepository.findByUser(user);
        return wishlistItems.stream()
                .map(wishlistItem -> {
                    WishlistItemDto dto = new WishlistItemDto();
                    dto.setProductId(wishlistItem.getProduct().orElseThrow().getProductId());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
