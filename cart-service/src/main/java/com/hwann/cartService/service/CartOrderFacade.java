package com.hwann.cartService.service;

import com.hwann.cartService.entity.User;

public interface CartOrderFacade {
    void moveItemsToOrderService(User user, Long cartItemIds);
}
