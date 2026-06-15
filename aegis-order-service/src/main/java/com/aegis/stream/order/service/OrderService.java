package com.aegis.stream.order.service;

import com.aegis.stream.order.event.OrderCreatedEvent;
import com.aegis.stream.order.exception.OrderNotFoundException;
import com.aegis.stream.order.kafka.OrderEventProducer;
import com.aegis.stream.order.model.Order;
import com.aegis.stream.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderEventProducer producer;

    public Order createOrder(UUID userId, Double amount) {

        Order order = new Order();
        order.setUserId(userId);
        order.setAmount(amount);

        Order saved = orderRepository.save(order);

        producer.publishOrderCreated(
                new OrderCreatedEvent(
                        saved.getId(),
                        saved.getUserId(),
                        saved.getAmount()
                )
        );

        return saved;
    }

    public void updateOrderStatus(UUID orderId, String paymentStatus) {

        orderRepository.findById(orderId).ifPresentOrElse(order -> {
            String newStatus = paymentStatus.equals("SUCCESS") ? "PAID" : "PAYMENT_FAILED";
            order.setStatus(newStatus);
            orderRepository.save(order);
            log.info("Order {} status updated to {}", orderId, newStatus);
        }, () -> log.warn("Order {} not found for status update", orderId));
    }

    public Order getOrder(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }
}