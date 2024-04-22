package com.hwann.marketmate.controller;

import com.hwann.marketmate.dto.WishlistItemDto;
import com.hwann.marketmate.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {
    private final UserService userService;


    @GetMapping("/wishlist")
    public ResponseEntity<List<WishlistItemDto>> getWishlist(Authentication authentication) throws Exception {
        List<WishlistItemDto> wishlistItems = userService.getUserWishlistItems(authentication);
        return ResponseEntity.ok(wishlistItems);
    }
}