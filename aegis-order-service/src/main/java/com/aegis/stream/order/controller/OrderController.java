package com.aegis.stream.order.controller;

import com.aegis.stream.order.model.Order;
import com.aegis.stream.order.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestParam Double amount) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = (UUID) auth.getPrincipal();

        return ResponseEntity.ok(orderService.createOrder(userId, amount));
    }
}
