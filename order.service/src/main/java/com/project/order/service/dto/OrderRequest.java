package com.project.order.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(description = "Request object for placing an order")
public record OrderRequest(
                @Schema(description = "Unique identifier of the order", example = "1") Long id,
                @Schema(description = "Order number", example = "ORD12345") String orderNumber,
                @Schema(description = "SKU code of the product", example = "SKU001") String skuCode,
                @Schema(description = "Price of the product", example = "99.99") BigDecimal price,
                @Schema(description = "Quantity of the product", example = "2") Integer quantity,
                @Schema(description = "User details associated with the order") UserDetails userDetails) {

        public record UserDetails(String email, String firstName, String lastName) {
        }
}
