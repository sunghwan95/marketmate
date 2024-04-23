package com.hwann.marketmate.controller;

import com.hwann.marketmate.dto.CartItemDto;
import com.hwann.marketmate.dto.WishlistItemDto;
import com.hwann.marketmate.entity.Product;
import com.hwann.marketmate.entity.User;
import com.hwann.marketmate.service.CartOrderFacade;
import com.hwann.marketmate.service.CartService;
import com.hwann.marketmate.service.ProductService;
import com.hwann.marketmate.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final UserService userService;
    private final ProductService productService;
    private final CartOrderFacade cartOrderFacade;

    @GetMapping
    public ResponseEntity<Set<CartItemDto>> getWishlist(
            Authentication authentication) {
        User user = userService.identifyUser(authentication);
        Set<CartItemDto> wishlistItems = cartService.getCartItemsForUser(user);
        return ResponseEntity.ok(wishlistItems);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addItemToCart(@RequestBody CartItemDto cartItemDto, Authentication authentication) {
        User user = userService.identifyUser(authentication);
        Product product = productService.getProductById(cartItemDto.getProductId());

        cartService.addItemToCart(user, product);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<?> removeItemFromCart(@PathVariable("cartItemId") Long cartItemId) {
        cartService.removeItemFromCart(cartItemId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update/{cartItemId}")
    public ResponseEntity<?> updateCartItemQuantity(@PathVariable("cartItemId") Long cartItemId, @RequestParam int quantity) {
        cartService.updateCartItemQuantity(cartItemId, quantity);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/deliver/selected")
    public ResponseEntity<?> checkoutSelectedItems(@RequestBody List<Long> cartItemIds, Authentication authentication) {
        User user = userService.identifyUser(authentication);

        cartOrderFacade.moveItemsToOrderService(user, cartItemIds);
        return ResponseEntity.ok().build();
    }
}
