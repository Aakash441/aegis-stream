package com.aegis.stream.payment.kafka;

import com.aegis.stream.payment.event.PaymentProcessedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishPaymentProcessed(PaymentProcessedEvent event) {
        kafkaTemplate.send("payment-processed-topic", event.getOrderId().toString(), event);
    }
}