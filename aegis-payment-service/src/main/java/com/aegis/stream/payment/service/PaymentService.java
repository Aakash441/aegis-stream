package com.aegis.stream.payment.service;

import com.aegis.stream.payment.event.OrderCreatedEvent;
import com.aegis.stream.payment.event.PaymentProcessedEvent;
import com.aegis.stream.payment.kafka.PaymentEventProducer;
import com.aegis.stream.payment.model.Payment;
import com.aegis.stream.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentEventProducer producer;

    public void processPayment(OrderCreatedEvent event) {
        Payment payment = new Payment();
        payment.setOrderId(event.getOrderId());
        payment.setUserId(event.getUserId());
        payment.setAmount(event.getAmount());
        payment.setStatus("PENDING");

        Payment saved = paymentRepository.save(payment);

        try {
            // TODO: integrate real payment gateway (Stripe, Razorpay, etc.)
            boolean success = chargePayment(event.getAmount());

            if (success) {
                saved.setStatus("SUCCESS");
                paymentRepository.save(saved);
                producer.publishPaymentProcessed(new PaymentProcessedEvent(
                        saved.getId(), event.getOrderId(), event.getUserId(),
                        event.getAmount(), "SUCCESS", null
                ));
            } else {
                saved.setStatus("FAILED");
                saved.setFailureReason("Payment declined");
                paymentRepository.save(saved);
                producer.publishPaymentProcessed(new PaymentProcessedEvent(
                        saved.getId(), event.getOrderId(), event.getUserId(),
                        event.getAmount(), "FAILED", "Payment declined"
                ));
            }
        } catch (Exception ex) {
            log.error("Payment processing error for order {}: {}", event.getOrderId(), ex.getMessage());
            saved.setStatus("FAILED");
            saved.setFailureReason(ex.getMessage());
            paymentRepository.save(saved);
            producer.publishPaymentProcessed(new PaymentProcessedEvent(
                    saved.getId(), event.getOrderId(), event.getUserId(),
                    event.getAmount(), "FAILED", ex.getMessage()
            ));
        }
    }

    private boolean chargePayment(Double amount) {
        // Stub: replace with real gateway call
        return amount != null && amount > 0;
    }
}