package com.aegis.stream.order.service;

import com.aegis.stream.order.event.OrderCreatedEvent;
import com.aegis.stream.order.kafka.OrderEventProducer;
import com.aegis.stream.order.model.Order;
import com.aegis.stream.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
}
