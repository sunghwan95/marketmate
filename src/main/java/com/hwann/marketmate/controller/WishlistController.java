package com.hwann.marketmate.controller;

import com.hwann.marketmate.dto.WishlistItemDto;
import com.hwann.marketmate.entity.User;
import com.hwann.marketmate.service.UserService;
import com.hwann.marketmate.service.WishlistCartFacade;
import com.hwann.marketmate.service.WishlistService;
import com.hwann.marketmate.service.implementation.WishlistCartFacadeImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wishlist")
public class WishlistController {
    private final UserService userService;
    private final WishlistService wishlistService;
    private final WishlistCartFacade wishlistCartFacade;

    @GetMapping
    public ResponseEntity<Set<WishlistItemDto>> getWishlist(
            Authentication authentication) {
        User user = userService.identifyUser(authentication);
        Set<WishlistItemDto> wishlistItems = wishlistService.getWishlistItemsForUser(user);
        return ResponseEntity.ok(wishlistItems);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addToWishlist(@RequestBody WishlistItemDto wishlistItemDto, Authentication authentication) {
        User user = userService.identifyUser(authentication);
        wishlistService.addItemToWishlist(wishlistItemDto, user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/remove/{wishlistItemId}")
    public ResponseEntity<?> removeFromWishlist(@PathVariable("wishlistItemId") Long wishlistItemId) {
        wishlistService.removeItemFromWishlist(wishlistItemId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/add-to-cart/{wishlistItemId}")
    public ResponseEntity<?> addToCart(@PathVariable("wishlistItemId") Long wishlistItemId, Authentication authentication) {
        User user = userService.identifyUser(authentication);
        wishlistCartFacade.transferItemToCart(user, wishlistItemId);
        return ResponseEntity.ok().build();
    }
}