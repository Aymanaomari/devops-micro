package com.project.order.service.service;

import com.project.order.service.client.InventoryClient;
import com.project.order.service.dto.OrderRequest;
import com.project.order.service.event.OrderPlacedEvent;
import com.project.order.service.model.Order;
import com.project.order.service.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class OrderService {

    final private OrderRepository orderRepository;
    final private InventoryClient inventoryClient;
    final private KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;
    final private Logger log = Logger.getLogger("logger");

    public void placeOrder(OrderRequest orderRequest) {

        var isProductInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());

        if (isProductInStock) {
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setPrice(orderRequest.price());
            order.setSkuCode(orderRequest.skuCode());
            order.setQuantity(orderRequest.quantity());
            orderRepository.save(order);
            // Send the message to kafka topic
            // orderNumber, email
            OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent(order.getOrderNumber(),
                    orderRequest.userDetails().email());
            log.info("OrderPlacedEvent sent to Kafka Topic: " + orderPlacedEvent);
            kafkaTemplate.send("order-placed", orderPlacedEvent);

        } else {
            log.warning("Product with skuCode " + orderRequest.skuCode() + " is not in stock");
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Product with skuCode " + orderRequest.skuCode() + " is not in stock");
        }

    }

}
