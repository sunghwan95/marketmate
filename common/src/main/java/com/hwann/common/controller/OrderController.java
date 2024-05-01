package com.hwann.common.controller;

import com.hwann.common.dto.OrderDto;
import com.hwann.common.service.implementation.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderServiceImpl orderService;


    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderStatus(@PathVariable("orderId") Long orderId) {
        OrderDto orderStatus = orderService.getOrderStatus(orderId);
        return ResponseEntity.ok(orderStatus);
    }

    @PostMapping("/cancel/{orderId}")
    public ResponseEntity<?> cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/return/{orderId}")
    public ResponseEntity<?> returnOrder(@PathVariable Long orderId) {
        orderService.returnOrder(orderId);
        return ResponseEntity.ok().build();
    }
}