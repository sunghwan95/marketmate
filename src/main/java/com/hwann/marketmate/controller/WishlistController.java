package com.hwann.marketmate.controller;

import com.hwann.marketmate.dto.WishlistItemDto;
import com.hwann.marketmate.entity.User;
import com.hwann.marketmate.service.UserService;
import com.hwann.marketmate.service.WishlistService;
import com.hwann.marketmate.service.implementation.WishlistCartFacadeImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wishlist")
public class WishlistController {
    private final UserService userService;
    private final WishlistService wishlistService;
    private final WishlistCartFacadeImpl wishlistCartFacade;

    //pass
    @GetMapping
    public ResponseEntity<Set<WishlistItemDto>> getWishlist(
            Authentication authentication) {
        User user = userService.identifyUser(authentication);
        Set<WishlistItemDto> wishlistItems = wishlistService.getWishlistItemsForUser(user);
        return ResponseEntity.ok(wishlistItems);
    }

    //pass
    @PostMapping("/add")
    @Transactional
    public ResponseEntity<?> addToWishlist(@RequestBody WishlistItemDto wishlistItemDto, Authentication authentication) {
        User user = userService.identifyUser(authentication);
        wishlistService.addItemToWishlist(wishlistItemDto, user);
        return ResponseEntity.ok().build();
    }

    //pass
    @DeleteMapping("/remove/{wishlistItemId}")
    @Transactional
    public ResponseEntity<?> removeFromWishlist(@PathVariable("wishlistItemId") Long wishlistItemId) {
        wishlistService.removeItemFromWishlist(wishlistItemId);
        return ResponseEntity.ok().build();
    }

    //pass
    @PostMapping("/add-to-cart")
    @Transactional
    public ResponseEntity<?> addToCart(@RequestParam("wishlistItemIds") List<Long> wishlistItemIds, Authentication authentication) {
        User user = userService.identifyUser(authentication);
        wishlistItemIds.forEach(id -> wishlistCartFacade.transferItemToCart(user, id));

        if (wishlistService.isWishlistEmpty(user)) {
            wishlistService.deleteWishlist(user);
        }

        return ResponseEntity.ok().build();
    }
}