package com.hwann.marketmate.service;

import com.hwann.marketmate.dto.OrderDto;

import java.util.List;

public interface OrderService {
    void cancelOrder(Long orderId) throws Exception;

    void returnOrder(Long orderId) throws Exception;

    void createOrderFromCartItems(Long userId, List<Long> cartItemIds);

    OrderDto getOrderStatus(Long orderId) throws Exception;
}