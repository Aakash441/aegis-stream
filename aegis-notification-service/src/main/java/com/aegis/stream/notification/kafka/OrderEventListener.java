package com.aegis.stream.notification.kafka;

import com.aegis.stream.notification.event.OrderCreatedEvent;
import com.aegis.stream.notification.event.PaymentProcessedEvent;
import com.aegis.stream.notification.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventListener {

    private final EmailService emailService;

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
    }

    @KafkaListener(
            topics = "payment-processed-topic",
            groupId = "notification-service",
            containerFactory = "paymentKafkaListenerContainerFactory"
    )
    public void handlePaymentProcessed(PaymentProcessedEvent event) {
        if ("SUCCESS".equals(event.getStatus())) {
            emailService.sendEmail(
                    "aakashvaishnav003@gmail.com",
                    "Payment Successful - Order " + event.getOrderId(),
                    "Your payment of ₹" + event.getAmount() + " for order " +
                            event.getOrderId() + " was successful."
            );
        } else {
            emailService.sendEmail(
                    "aakashvaishnav003@gmail.com",
                    "Payment Failed - Order " + event.getOrderId(),
                    "Your payment for order " + event.getOrderId() +
                            " failed. Reason: " + event.getReason()
            );
        }
    }
}

