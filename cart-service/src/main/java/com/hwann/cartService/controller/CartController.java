package com.hwann.cartService.controller;

import com.hwann.cartService.dto.CartItemDto;
import com.hwann.cartService.entity.CartItem;
import com.hwann.cartService.entity.Product;
import com.hwann.cartService.entity.User;
import com.hwann.cartService.service.implementation.CartOrderFacadeImpl;
import com.hwann.cartService.service.implementation.CartServiceImpl;
import com.hwann.cartService.service.implementation.ProductServiceImpl;
import com.hwann.cartService.service.implementation.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final UserServiceImpl userService;
    private final CartServiceImpl cartService;
    private final ProductServiceImpl productService;
    private final CartOrderFacadeImpl cartOrderFacade;

    @GetMapping
    public ResponseEntity<Set<CartItem>> getCartItems(
            Authentication authentication) {
        User user = userService.identifyUser(authentication);
        Set<CartItem> cartItems = cartService.getCartItemsForUser(user);
        return ResponseEntity.ok(cartItems);
    }

    @PostMapping
    public ResponseEntity<?> addItemToCart(@RequestBody CartItemDto cartItemDto, Authentication authentication) {
        User user = userService.identifyUser(authentication);
        Optional<Product> productOptional = productService.getProductById(cartItemDto.getProductId());

        if (productOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Product product = productOptional.get();
        cartService.addItemToCart(user, product);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<?> removeItemFromCart(@PathVariable("cartItemId") Long cartItemId) {
        cartService.removeItemFromCart(cartItemId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{cartItemId}")
    public ResponseEntity<?> updateCartItemQuantity(@PathVariable("cartItemId") Long cartItemId, @RequestParam("quantity") int quantity) {
        cartService.updateCartItemQuantity(cartItemId, quantity);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/selected-order")
    public ResponseEntity<?> checkoutSelectedItems(@RequestParam("cartItemIds") List<Long> cartItemIds, Authentication authentication) {
        User user = userService.identifyUser(authentication);
        cartItemIds.forEach(id -> cartOrderFacade.moveItemsToOrderService(user, id));
        return ResponseEntity.ok().build();
    }
}
