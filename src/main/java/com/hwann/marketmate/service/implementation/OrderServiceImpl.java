package com.hwann.marketmate.service.implementation;

import com.hwann.marketmate.dto.OrderDto;
import com.hwann.marketmate.entity.*;
import com.hwann.marketmate.repository.CartItemRepository;
import com.hwann.marketmate.repository.OrderRepository;
import com.hwann.marketmate.repository.ProductRepository;
import com.hwann.marketmate.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public void createOrderFromCartItems(User user, List<Long> cartItemIds) {
        List<CartItem> cartItems = cartItemRepository.findAllById(cartItemIds);
        if (cartItems.isEmpty()) {
            throw new IllegalArgumentException("No cart items found for given IDs");
        }

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderItems(new HashSet<>());

        for (CartItem cartItem : cartItems) {
            OrderDetail orderItem = new OrderDetail();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setOrder(order);
            order.getOrderItems().add(orderItem);
        }

        orderRepository.save(order);
        cartItemRepository.deleteAll(cartItems);
    }

    @Override
    public OrderDto getOrderStatus(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        updateOrderStatus(order);

        OrderDto orderDto = new OrderDto();
        orderDto.setUserId(order.getUser().getUserId());
        // 주문 상품 목록을 OrderItemDto로 변환하여 설정하는 로직을 추가합니다.

        return orderDto;
    }

    @Override
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        updateOrderStatus(order);

        if (order.getStatus() == OrderStatus.PENDING) {
            order.setStatus(OrderStatus.CANCELED);

            order.getOrderItems().forEach(item -> {
                Product product = item.getProduct();
                product.setStock(product.getStock() + item.getQuantity());
                productRepository.save(product);
            });

            orderRepository.save(order);
        } else {
            throw new IllegalStateException("Order can only be cancelled if it is in PENDING status.");
        }
    }

    @Override
    public void returnOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        updateOrderStatus(order);

        if (order.getStatus() == OrderStatus.COMPLETED) {
            order.setStatus(OrderStatus.RETURNED);

            order.getOrderItems().forEach(item -> {
                Product product = item.getProduct();
                product.setStock(product.getStock() + item.getQuantity());
                productRepository.save(product);
            });

            orderRepository.save(order);
        } else {
            throw new IllegalStateException("Order can only be returned if it is in COMPLETED status.");
        }
    }

    private void updateOrderStatus(Order order) {
        LocalDateTime now = LocalDateTime.now();
        if (order.getStatus() == OrderStatus.PENDING && now.isAfter(order.getOrderDate().plusDays(1))) {
            order.setStatus(OrderStatus.PROCESSING);
        } else if (order.getStatus() == OrderStatus.PROCESSING && now.isAfter(order.getOrderDate().plusDays(2))) {
            order.setStatus(OrderStatus.COMPLETED);
        }
        orderRepository.save(order);
    }
}
