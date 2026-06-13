package com.aegis.stream.order.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentProcessedEvent {
    private UUID paymentId;
    private UUID orderId;
    private UUID userId;
    private Double amount;
    private String status;
    private String reason;
}