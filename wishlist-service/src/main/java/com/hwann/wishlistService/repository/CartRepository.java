package com.hwann.wishlistService.repository;

import com.hwann.wishlistService.entity.Cart;
import com.hwann.wishlistService.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}