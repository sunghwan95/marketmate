package com.hwann.marketmate.service.implementation;

import com.hwann.marketmate.dto.WishlistItemDto;
import com.hwann.marketmate.entity.*;
import com.hwann.marketmate.repository.*;
import com.hwann.marketmate.service.ProductService;
import com.hwann.marketmate.service.UserService;
import com.hwann.marketmate.service.WishlistService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class WishlistServiceImpl implements WishlistService {
    private final WishlistRepository wishlistRepository;
    private final WishlistItemRepository wishlistItemRepository;
    private final UserService userService;
    private final ProductService productService;

    @Override
    public Set<WishlistItemDto> getWishlistItemsForUser(Authentication authentication) {
        User user = userService.identifyUser(authentication);

        Wishlist wishlist = wishlistRepository.findByUserId(user.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Wishlist not found for user"));

        return wishlist.getItems().stream()
                .map(this::convertToDto)
                .collect(Collectors.toSet());
    }

    @Override
    public void addItemToWishlist(WishlistItemDto wishlistItemDto, Authentication authentication) {
        User user = userService.identifyUser(authentication);
        Product product = productService.getProductById(wishlistItemDto.getProductId());

        Wishlist wishlist = wishlistRepository.findByUserId(user.getUserId())
                .orElseThrow(() -> new RuntimeException("Wishlist not found"));

        WishlistItem wishlistItem = new WishlistItem();
        wishlistItem.setProduct(product);
        wishlistItem.setWishlist(wishlist);
        wishlistItemRepository.save(wishlistItem);
    }


    @Override
    public void removeItemFromWishlist(Long wishlistItemId) {
        wishlistItemRepository.deleteById(wishlistItemId);
    }

    @Override
    public Optional<WishlistItem> findWishlistItemById(Long wishlistItemId){
        return wishlistItemRepository.findById(wishlistItemId);
    }

    private WishlistItemDto convertToDto(WishlistItem item) {

        WishlistItemDto dto = new WishlistItemDto();
        dto.setProductId(item.getProduct().getProductId());
        dto.setProductName(item.getProduct().getName());

        return dto;
    }
}
