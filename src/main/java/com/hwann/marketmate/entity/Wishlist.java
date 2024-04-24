package com.hwann.marketmate.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "wishlists")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "wishlist", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<WishlistItem> items = new HashSet<>();

    public static class WishlistBuilder {
        public Wishlist.WishlistBuilder items(Set<WishlistItem> items) {
            if (items == null) {
                this.items = new HashSet<>();
            } else {
                this.items = new HashSet<>(items);
            }
            return this;
        }
    }
}