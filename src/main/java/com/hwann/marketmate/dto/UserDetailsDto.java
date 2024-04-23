package com.hwann.marketmate.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Data
@NoArgsConstructor
public class UserDetailsDto {
    private Long userId;
    private String username;
    private String email;
    // 장바구니 아이템을 설정하는 메소드
    @Setter
    private Set<CartItemDto> cartItems;
    // 위시리스트 아이템을 설정하는 메소드
    @Setter
    private Set<WishlistItemDto> wishlistItems;

    // 필수 필드만 초기화하는 생성자
    public UserDetailsDto(Long userId, String username, String email) {
        this.userId = userId;
        this.username = username;
        this.email = email;
    }
}
