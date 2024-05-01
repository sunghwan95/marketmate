package com.hwann.orderService.service.implementation;

import com.hwann.orderService.dto.OrderDto;
import com.hwann.orderService.entity.*;
import com.hwann.orderService.repository.CartItemRepository;
import com.hwann.orderService.repository.OrderRepository;
import com.hwann.orderService.repository.ProductRepository;
import com.hwann.orderService.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public void createOrderFromCartItems(User user, Long cartItemId) {
        Optional<CartItem> cartItem = cartItemRepository.findById(cartItemId);
        if (cartItem.isEmpty()) {
            throw new IllegalArgumentException("No cart items found for given IDs");
        }

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderDetails(new HashSet<>());


        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProduct(cartItem.get().getProduct());
        orderDetail.setQuantity(cartItem.get().getQuantity());
        orderDetail.setOrder(order);
        order.getOrderDetails().add(orderDetail);


        orderRepository.save(order);
        cartItemRepository.deleteById(cartItemId);
    }

    @Override
    public OrderDto getOrderStatus(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        updateOrderStatus(order);

        OrderDto orderDto = new OrderDto();
        orderDto.setUserId(order.getUser().getUserId());

        return orderDto;
    }

    @Override
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        updateOrderStatus(order);

        if (order.getStatus() == OrderStatus.PENDING) {
            order.setStatus(OrderStatus.CANCELED);

            order.getOrderDetails().forEach(item -> {
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

            order.getOrderDetails().forEach(item -> {
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