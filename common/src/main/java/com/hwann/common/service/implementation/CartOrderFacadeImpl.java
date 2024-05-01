package com.hwann.common.service.implementation;

import com.hwann.common.entity.User;
import com.hwann.common.service.CartOrderFacade;
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
