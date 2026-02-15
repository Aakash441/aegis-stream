package com.aegis.stream.order.controller;

import com.aegis.stream.order.model.Order;
import com.aegis.stream.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(
            @RequestParam UUID userId,
            @RequestParam Double amount
    ) {
        return ResponseEntity.ok(orderService.createOrder(userId, amount));
    }
}
