package com.hwann.common.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderDto {
    private Long userId; // 주문을 한 사용자 ID
    private List<OrderItemDto> orderItems; // 주문한 상품 목록
}