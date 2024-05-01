package com.hwann.cartService.service.implementation;

import com.hwann.cartService.service.CartOrderFacade;
import com.hwann.cartService.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartOrderFacadeImpl implements CartOrderFacade {
    private final OrderServiceImpl orderService;

    @Override
    public void moveItemsToOrderService(User user, Long cartItemIds) {
        orderService.createOrderFromCartItems(user, cartItemIds);
    }
}
