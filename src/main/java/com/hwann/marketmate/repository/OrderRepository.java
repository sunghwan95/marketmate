package com.hwann.marketmate.repository;

import com.hwann.marketmate.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // 추가적인 메서드가 필요한 경우 여기에 작성할 수 있습니다.
}
