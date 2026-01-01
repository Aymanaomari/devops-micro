package com.project.product.service.client;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.PostExchange;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface InventoryClient {

    Logger log = LoggerFactory.getLogger(InventoryClient.class);

    @PostExchange("/api/inventory")
    @CircuitBreaker(name = "inventoryService", fallbackMethod = "fallbackAddToInventory")
    @Retry(name = "inventoryService")
    boolean addToInventory(@RequestParam String productId, @RequestParam String productName,
            @RequestParam Integer quantity);

    default boolean fallbackAddToInventory(String productId, String productName, Integer quantity,
            Throwable throwable) {
        log.error("Inventory service is down. Fallback method invoked for addToInventory with productId: {}",
                productId);
        return false; // Assume addition to inventory failed when the service is down
    }
}
