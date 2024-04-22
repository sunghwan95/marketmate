package com.hwann.marketmate.dto;

import com.hwann.marketmate.entity.OrderItem;
import lombok.Data;

import java.util.List;

@Data
public class OrderDto {
    private Long userId; // 주문을 한 사용자 ID
    private List<OrderItemDto> orderItems; // 주문한 상품 목록
    // 추가적인 필드가 있을 경우 여기에 작성할 수 있습니다.
}