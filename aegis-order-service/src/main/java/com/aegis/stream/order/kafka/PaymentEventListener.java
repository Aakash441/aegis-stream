package com.aegis.stream.order.kafka;

import com.aegis.stream.order.event.PaymentProcessedEvent;
import com.aegis.stream.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentEventListener {

    private final OrderService orderService;

    @KafkaListener(
            topics = "payment-processed-topic",
            groupId = "order-service",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handlePaymentProcessed(PaymentProcessedEvent event) {
        orderService.updateOrderStatus(event.getOrderId(), event.getStatus());
    }
}