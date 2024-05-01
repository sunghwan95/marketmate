package com.hwann.common.service;

import com.hwann.common.dto.OrderDto;
import com.hwann.common.entity.User;

public interface OrderService {
    void cancelOrder(Long orderId) throws Exception;

    void returnOrder(Long orderId) throws Exception;

    void createOrderFromCartItems(User user, Long cartItemId);

    OrderDto getOrderStatus(Long orderId) throws Exception;
}