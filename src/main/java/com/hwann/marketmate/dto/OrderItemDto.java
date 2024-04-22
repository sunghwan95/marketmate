package com.hwann.marketmate.dto;

import lombok.Data;

@Data
public class OrderItemDto {
    private Long productId;
    private int quantity;
    // 나머지 필드들 (생략)
}
