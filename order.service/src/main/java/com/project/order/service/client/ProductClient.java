package com.project.order.service.client;

import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.bind.annotation.PathVariable;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface ProductClient {

    @GetExchange("/api/product/{id}/price")
    @CircuitBreaker(name = "productService", fallbackMethod = "getProductByIdFallback")
    @Retry(name = "productService")
    BigDecimal getProductById(@PathVariable String id);

    default BigDecimal getProductByIdFallback(String id, Throwable throwable) {
        Logger log = LoggerFactory.getLogger(ProductClient.class);
        log.info("Cannot get product for id {}, failure reason: {}", id, throwable.getMessage());
        return null;
    }
}
