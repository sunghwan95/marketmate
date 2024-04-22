package com.hwann.marketmate.service.implementation;

import com.hwann.marketmate.dto.OrderDto;
import com.hwann.marketmate.entity.Order;
import com.hwann.marketmate.entity.OrderStatus;
import com.hwann.marketmate.repository.OrderRepository;
import com.hwann.marketmate.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

}
