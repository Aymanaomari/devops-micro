package com.project.order.service.controller;

import com.project.order.service.dto.OrderRequest;
import com.project.order.service.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Tag(name = "Order Controller", description = "APIs for managing orders")
public class OrderController {

    private final OrderService orderservice;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Place an order", description = "Creates a new order based on the provided order request")
    public String placeOrder(@RequestBody OrderRequest orderRequest) {

        orderservice.placeOrder(orderRequest);
        return "Order Placed Successfully";
    }

    @GetMapping("/test")
    @Operation(summary = "Test Endpoint", description = "A simple test endpoint to verify the service is running")
    public String testEndpoint() {
        return "Order Service is up and running!";
    }

}
