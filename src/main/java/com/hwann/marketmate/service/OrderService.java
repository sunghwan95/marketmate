package com.hwann.marketmate.service;

import com.hwann.marketmate.dto.OrderDto;
import com.hwann.marketmate.entity.Order;

import java.util.List;

public interface OrderService {
    public Order placeOrder(OrderDto orderDto, Long userId);

    public List<Order> getAllOrders(Long userId);

    public Order cancelOrder(Long orderId) ;

    public Order returnOrder(Long orderId);
}
