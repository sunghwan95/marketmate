package com.hwann.marketmate.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "wishlists")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishlistId;

    private Long userId;

    private Long productId;

    private String productName;
}
