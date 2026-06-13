package com.aegis.stream.payment.kafka;

import com.aegis.stream.payment.event.OrderCreatedEvent;
import com.aegis.stream.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventListener {

    private final PaymentService paymentService;

    @KafkaListener(
            topics = "order-created-topic",
            groupId = "payment-service",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleOrderCreated(OrderCreatedEvent event) {
        paymentService.processPayment(event);
    }
}