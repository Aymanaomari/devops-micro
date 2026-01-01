package com.project.order.service.client;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface InventoryClient {

    Logger log = LoggerFactory.getLogger(InventoryClient.class);

    @GetExchange("/api/inventory")
    @CircuitBreaker(name = "inventoryService", fallbackMethod = "isInStockFallback")
    @Retry(name = "inventoryService")
    boolean isInStock(@RequestParam("productId") String productId,
            @RequestParam("quantity") Integer quantity);

    default boolean isInStockFallback(String ProductId, Integer quantity, Throwable throwable) {
        log.info("Cannot get inventory for ProductId {}, failure reason: {}", ProductId, throwable.getMessage());
        return false;
    }
}
