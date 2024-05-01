package com.hwann.orderService.service;

import com.hwann.orderService.dto.OrderDto;
import com.hwann.orderService.entity.User;

public interface OrderService {
    void cancelOrder(Long orderId) throws Exception;

    void returnOrder(Long orderId) throws Exception;

    void createOrderFromCartItems(User user, Long cartItemId);

    OrderDto getOrderStatus(Long orderId) throws Exception;
}