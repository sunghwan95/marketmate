package com.hwann.marketmate.service;

import com.hwann.marketmate.entity.User;

import java.util.List;

public interface CartOrderFacade {
    void moveItemsToOrderService(User user, List<Long> cartItemIds);
}
