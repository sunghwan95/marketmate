package com.hwann.marketmate.controller;

import com.hwann.marketmate.dto.CartItemDto;
import com.hwann.marketmate.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addItemToCart(@RequestParam Long userId, @RequestBody CartItemDto cartItemDto) {
        cartService.addItemToCart(userId, cartItemDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<?> removeItemFromCart(@PathVariable Long cartItemId) {
        cartService.removeItemFromCart(cartItemId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update/{cartItemId}")
    public ResponseEntity<?> updateCartItemQuantity(@PathVariable Long cartItemId, @RequestParam int quantity) {
        cartService.updateCartItemQuantity(cartItemId, quantity);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/checkout/selected")
    public ResponseEntity<?> checkoutSelectedItems(@RequestParam Long userId, @RequestBody List<Long> cartItemIds) {
        cartService.checkout(userId, cartItemIds);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/checkout/all")
    public ResponseEntity<?> checkoutAllItems(@RequestParam Long userId) {
        cartService.checkoutAll(userId);
        return ResponseEntity.ok().build();
    }
}
