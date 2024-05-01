package com.hwann.cartService.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartItemDto {
    private Long productId;
    private String productName;  // 제품 이름을 저장할 필드 추가
    private int quantity;

    // 생성자를 추가하여 제품 ID, 이름, 수량을 초기화
    public CartItemDto(Long productId, String productName, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
    }
}
