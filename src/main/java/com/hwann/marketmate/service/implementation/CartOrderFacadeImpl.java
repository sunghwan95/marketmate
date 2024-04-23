package com.hwann.marketmate.service.implementation;

import com.hwann.marketmate.entity.User;
import com.hwann.marketmate.service.CartOrderFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartOrderFacadeImpl implements CartOrderFacade {
    private final OrderServiceImpl orderService;

    @Override
    public void moveItemsToOrderService(User user, List<Long> cartItemIds) {
        orderService.createOrderFromCartItems(user, cartItemIds);
    }
}