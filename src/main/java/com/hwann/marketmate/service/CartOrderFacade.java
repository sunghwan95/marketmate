package com.hwann.marketmate.service;

import com.hwann.marketmate.entity.User;

public interface CartOrderFacade {
    void moveItemsToOrderService(User user, Long cartItemIds);
}
