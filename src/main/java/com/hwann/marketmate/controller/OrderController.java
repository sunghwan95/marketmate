package com.hwann.marketmate.controller;

import com.hwann.marketmate.dto.OrderDto;
import com.hwann.marketmate.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;


    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderStatus(@PathVariable Long orderId) throws Exception {
        OrderDto orderStatus = orderService.getOrderStatus(orderId);
        return ResponseEntity.ok(orderStatus);
    }

    @PostMapping("/cancel/{orderId}")
    public ResponseEntity<?> cancelOrder(@PathVariable Long orderId) throws Exception {
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/return/{orderId}")
    public ResponseEntity<?> returnOrder(@PathVariable Long orderId) throws Exception {
        orderService.returnOrder(orderId);
        return ResponseEntity.ok().build();
    }
}