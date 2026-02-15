package com.aegis.stream.order.event;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class OrderCreatedEvent {
    private UUID orderId;
    private UUID userId;
    private Double amount;
}
