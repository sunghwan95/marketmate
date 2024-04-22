package com.hwann.marketmate.controller;

import com.hwann.marketmate.dto.WishlistItemDto;
import com.hwann.marketmate.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wishlist")
public class WishlistController {
    private final WishlistService wishlistService;

    // 상품을 위시리스트에 추가하는 엔드포인트
    @PostMapping("/add")
    public ResponseEntity<?> addToWishlist(@RequestBody WishlistItemDto wishlistItemDto) {
        wishlistService.addToWishlist(wishlistItemDto);
        return ResponseEntity.ok().build();
    }

    // 위시리스트에서 상품을 삭제하는 엔드포인트
    @DeleteMapping("/remove/{wishlistItemId}")
    public ResponseEntity<?> removeFromWishlist(@PathVariable("wishlistItemId") Long wishlistItemId) {
        wishlistService.removeFromWishlist(wishlistItemId);
        return ResponseEntity.ok().build();
    }

    // 위시리스트에 있는 상품을 장바구니에 추가하는 엔드포인트
    @PostMapping("/add-to-cart/{wishlistItemId}")
    public ResponseEntity<?> addToCart(@PathVariable("wishlistItemId") Long wishlistItemId) {
        wishlistService.addToCart(wishlistItemId);
        return ResponseEntity.ok().build();
    }
}