package com.hwann.orderService.service.implementation;

import com.hwann.orderService.dto.WishlistItemDto;
import com.hwann.orderService.entity.Product;
import com.hwann.orderService.entity.User;
import com.hwann.orderService.entity.Wishlist;
import com.hwann.orderService.service.WishlistService;
import com.hwann.orderService.repository.WishlistRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {
    private final WishlistRepository wishlistRepository;
    private final ProductServiceImpl productService;

    @Override
    @Transactional
    public Set<WishlistItemDto> getWishlistItemsForUser(User user) {
        Wishlist wishlist = wishlistRepository.findById(user.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Wishlist not found for user"));

        return wishlist.getProducts().stream()
                .map(this::convertToDto)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public void addItemToWishlist(User user, Long productId) {
        Product product = productService.getProductById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found")); // Product가 없는 경우 예외 처리

        Wishlist wishlist = wishlistRepository.findByUser(user)
                .orElseGet(() -> {
                    Wishlist newWishlist = Wishlist.builder()
                            .user(user)
                            .build();
                    return wishlistRepository.save(newWishlist);
                });

        if (!wishlist.getProducts().contains(product)) {
            wishlist.getProducts().add(product);
            wishlistRepository.save(wishlist);
        } else {
            throw new IllegalStateException("Product already in wishlist");
        }
    }


    @Override
    @Transactional
    public void removeItemFromWishlist(User user, Long productId) {
        Wishlist wishlist = wishlistRepository.findByUser(user)
                .orElseThrow(() -> new IllegalStateException("Wishlist not found"));
        Product product = productService.getProductById(productId)
                .orElseThrow(() -> new IllegalStateException("Product not found in wishlist"));

        wishlist.getProducts().remove(product);
        wishlistRepository.save(wishlist);
    }


    @Override
    @Transactional
    public void deleteWishlist(User user) {
        Wishlist wishlist = wishlistRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Wishlist not found for user"));

        wishlistRepository.delete(wishlist);
    }

    @Override
    @Transactional
    public Optional<Product> findWishlistItemById(Long wishlistId, Long productId) {
        Optional<Wishlist> wishlist = wishlistRepository.findById(wishlistId);
        return wishlist.flatMap(value -> value.getProducts().stream()
                .filter(product -> product.getProductId().equals(productId))
                .findFirst());
    }

    @Override
    @Transactional
    public boolean isWishlistEmpty(User user) {
        Wishlist wishlist = wishlistRepository.findByUser(user)
                .orElseThrow(() -> new IllegalStateException("Wishlist not found for user"));
        return wishlist.getProducts().isEmpty();
    }

    @Override
    @Transactional
    public Optional<Product> findProductById(Long productId) {
        return productService.getProductById(productId);
    }

    private WishlistItemDto convertToDto(Product product) {
        return new WishlistItemDto(product.getProductId(), product.getName());
    }
}
