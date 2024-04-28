package com.hwann.marketmate.controller;

import com.hwann.marketmate.dto.WishlistItemDto;
import com.hwann.marketmate.entity.User;
import com.hwann.marketmate.service.implementation.UserServiceImpl;
import com.hwann.marketmate.service.implementation.WishlistCartFacadeImpl;
import com.hwann.marketmate.service.implementation.WishlistServiceImpl;
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
    private final UserServiceImpl userService;
    private final WishlistServiceImpl wishlistService;
    private final WishlistCartFacadeImpl wishlistCartFacade;

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
        wishlistService.addItemToWishlist(user, wishlistItemDto.getProductId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/remove/{wishlistItemId}")
    @Transactional
    public ResponseEntity<?> removeFromWishlist(@PathVariable("wishlistItemId") Long wishlistItemId, Authentication authentication) {
        User user = userService.identifyUser(authentication);
        wishlistService.removeItemFromWishlist(user, wishlistItemId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/add-to-cart")
    public ResponseEntity<?> addToCart(@RequestParam("wishlistItemIds") List<Long> wishlistItemIds, Authentication authentication) {
        User user = userService.identifyUser(authentication);
        wishlistItemIds.forEach(id -> wishlistCartFacade.transferItemToCart(user, id));

        if (wishlistService.isWishlistEmpty(user)) {
            wishlistService.deleteWishlist(user);
        }

        return ResponseEntity.ok().build();
    }
}