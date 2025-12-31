package com.project.product.service.client;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface InventoryClient {

    Logger log = LoggerFactory.getLogger(InventoryClient.class);

    @GetExchange("/api/inventory")
    @CircuitBreaker(name = "inventoryService", fallbackMethod = "isInStockFallback")
    @Retry(name = "inventoryService")
    boolean isInStock(@RequestParam String skuCode, @RequestParam Integer quantity);

    @PostExchange("/api/inventory")
    @CircuitBreaker(name = "inventoryService", fallbackMethod = "falbackAddToInventory")
    @Retry(name = "inventoryService")
    boolean addToInventory(@RequestParam String skuCode, @RequestParam Integer quantity);

    default boolean isInStockFallback(String skuCode, Integer quantity, Throwable throwable) {
        log.error("Inventory service is down. Fallback method invoked for isInStock with skuCode: {}", skuCode);
        return false; // Assume not in stock when the service is down
    }

    default boolean falbackAddToInventory(String skuCode, Integer quantity, Throwable throwable) {
        log.error("Inventory service is down. Fallback method invoked for addToInventory with skuCode: {}", skuCode);
        return false; // Assume addition to inventory failed when the service is down
    }
}
