package com.hwann.marketmate.controller;

import com.hwann.marketmate.dto.WishlistItemDto;
import com.hwann.marketmate.entity.User;
import com.hwann.marketmate.service.UserService;
import com.hwann.marketmate.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/wishlists")
@RequiredArgsConstructor
public class WishlistController {
    private final UserService userService;
    private final WishlistService wishlistService;

    // 상품을 위시리스트에 등록하는 기능
    @PostMapping("/wishlist/{productId}")
    public ResponseEntity<String> addToWishlist(@PathVariable("productId") Long productId, Authentication authentication) {
        try {
            User user = userService.getCurrentUser(authentication);
            wishlistService.addToWishlist(productId, user);
            return ResponseEntity.ok("상품이 위시리스트에 추가되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("위시리스트에 상품을 추가하는 중 오류가 발생했습니다.");
        }
    }

    // 위시리스트에서 상품을 제거하는 기능
    @DeleteMapping("/wishlist/{wishlistItemId}")
    public ResponseEntity<String> removeFromWishlist(@PathVariable Long wishlistItemId) {
        try {
            wishlistService.removeFromWishlist(wishlistItemId);
            return ResponseEntity.ok("상품이 위시리스트에서 제거되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("위시리스트에서 상품을 제거하는 중 오류가 발생했습니다.");
        }
    }

    // 위시리스트 조회 기능
    @GetMapping("/wishlist")
    public ResponseEntity<?> getWishlist(Authentication authentication) {
        try {
            User user = userService.getCurrentUser(authentication);
            List<WishlistItemDto> wishlistItems = wishlistService.getWishlist(user);
            return ResponseEntity.ok(wishlistItems);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("위시리스트를 가져오는 중 오류가 발생했습니다.");
        }
    }
}
