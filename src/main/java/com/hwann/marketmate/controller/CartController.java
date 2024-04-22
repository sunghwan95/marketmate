package com.hwann.marketmate.controller;

import com.hwann.marketmate.dto.CartItemDto;
import com.hwann.marketmate.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;


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

    @PostMapping("/deliver/selected")
    public ResponseEntity<?> checkoutSelectedItems(@RequestParam Long userId, @RequestBody List<Long> cartItemIds) {
        cartService.moveItemsToOrderService(userId, cartItemIds);
        return ResponseEntity.ok().build();
    }
}
