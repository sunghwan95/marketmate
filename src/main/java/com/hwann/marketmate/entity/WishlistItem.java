package com.hwann.marketmate.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Optional;

@Entity(name = "wishlist_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WishlistItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishlistItemId;

    @ManyToOne
    @JoinColumn(name = "userId")
    public User user;

    @ManyToOne
    @JoinColumn(name = "productId")
    public Optional<Product> product;
}
