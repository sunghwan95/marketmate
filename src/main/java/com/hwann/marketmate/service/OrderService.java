package com.hwann.marketmate.service;

import com.hwann.marketmate.dto.OrderDto;
import com.hwann.marketmate.entity.User;

import java.util.List;

public interface OrderService {
    void cancelOrder(Long orderId) throws Exception;

    void returnOrder(Long orderId) throws Exception;

    void createOrderFromCartItems(User user, Long cartItemId);

    OrderDto getOrderStatus(Long orderId) throws Exception;
}