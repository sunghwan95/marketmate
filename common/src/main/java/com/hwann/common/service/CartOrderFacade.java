package com.hwann.common.service;

import com.hwann.common.entity.User;

public interface CartOrderFacade {
    void moveItemsToOrderService(User user, Long cartItemIds);
}
