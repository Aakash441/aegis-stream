package com.aegis.stream.notification.kafka;

import com.aegis.stream.notification.event.OrderCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListener {

    @KafkaListener(
            topics = "order-created-topic",
            groupId = "notification-service",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleOrderCreated(OrderCreatedEvent event) {

        System.out.println("📨 Notification Service received order event:");
        System.out.println("Order ID: " + event.getOrderId());
        System.out.println("User ID: " + event.getUserId());
        System.out.println("Amount: " + event.getAmount());

        // Later:
        // sendEmail()
        // sendSMS()
        // pushNotification()
    }
}

