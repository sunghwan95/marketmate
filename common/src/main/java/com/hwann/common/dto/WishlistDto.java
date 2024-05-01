package com.hwann.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WishlistDto {
    private Long userId;
    private Set<WishlistItemDto> items;
}
