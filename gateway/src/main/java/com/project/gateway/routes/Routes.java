package com.project.gateway.routes;

import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.path;

import java.net.URI;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.setPath;
import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class Routes {

        @Value("${product.service.url}")
        private String productServiceUrl;

        @Value("${order.service.url}")
        private String orderServiceUrl;

        @Value("${inventory.service.url}")
        private String inventoryServiceUrl;

        @Bean
        public RouterFunction<ServerResponse> productServiceRoute() {
                return route("product_route")
                                .route(path("/api/product/**"), http())
                                .before(uri(productServiceUrl))
                                .filter(CircuitBreakerFilterFunctions.circuitBreaker("productServiceCircuitBreaker",
                                                URI.create("forward:/fallback")))
                                .build();
        }

        @Bean
        public RouterFunction<ServerResponse> productServiceSwaggerRoute() {
                return route("product_route_swagger")
                                .route(path("/aggregate/product-service/v1/api-docs"), http())
                                .before(uri(productServiceUrl)).before(setPath("/api-docs"))
                                .filter(CircuitBreakerFilterFunctions.circuitBreaker(
                                                "productServiceSwaggerCircuitBreaker",
                                                URI.create("forward:/fallback")))
                                .build();
        }

        @Bean
        public RouterFunction<ServerResponse> orderServiceRoute() {
                return route("order_route")
                                .route(path("/api/order/**"), http())
                                .before(uri(orderServiceUrl))
                                .filter(CircuitBreakerFilterFunctions.circuitBreaker("orderServiceCircuitBreaker",
                                                URI.create("forward:/fallback")))
                                .build();
        }

        @Bean
        public RouterFunction<ServerResponse> orderServiceSwaggerRoute() {
                return route("order_route_swagger")
                                .route(path("/aggregate/order-service/v1/api-docs"), http())
                                .before(uri(orderServiceUrl)).before(setPath("/api-docs"))
                                .filter(CircuitBreakerFilterFunctions.circuitBreaker(
                                                "orderServiceSwaggerCircuitBreaker",
                                                URI.create("forward:/fallback")))
                                .build();
        }

        @Bean
        public RouterFunction<ServerResponse> inventoryServiceRoute() {
                return route("inventory_route")
                                .route(path("/api/inventory/**"), http())
                                .before(uri(inventoryServiceUrl))
                                .filter(CircuitBreakerFilterFunctions.circuitBreaker("inventoryServiceCircuitBreaker",
                                                URI.create("forward:/fallback")))
                                .build();
        }

        @Bean
        public RouterFunction<ServerResponse> inventoryServiceSwaggerRoute() {
                return route("inventory_route_swagger")
                                .route(path("/aggregate/inventory-service/v1/api-docs"), http())
                                .before(uri(inventoryServiceUrl)).before(setPath("/api-docs"))
                                .filter(CircuitBreakerFilterFunctions.circuitBreaker(
                                                "inventoryServiceSwaggerCircuitBreaker",
                                                URI.create("forward:/fallback")))
                                .build();
        }

        @Bean
        public RouterFunction<ServerResponse> fallbackRoute() {
                return route("fallback_route")
                                .route(path("/fallback"),
                                                request -> ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                                                                .body("Service is currently unavailable"))
                                .build();
        }
}
