package com.hwann.marketmate.controller;

import com.hwann.marketmate.dto.OrderDto;
import com.hwann.marketmate.entity.Order;
import com.hwann.marketmate.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

}
