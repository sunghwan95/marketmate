package com.hwann.marketmate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WishlistDto {
    private Long userId;
    private List<WishlistItemDto> wishlistItems;
}
