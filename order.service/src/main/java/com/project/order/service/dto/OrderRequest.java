package com.project.order.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request object for placing an order")
public record OrderRequest(
                @Schema(description = "Product ID", example = "123") String productId,
                @Schema(description = "Name of the product", example = "Laptop") String productName,
                @Schema(description = "Quantity of the product", example = "2") Integer quantity,
                @Schema(description = "User details associated with the order") UserDetails userDetails) {

        public record UserDetails(String email, String firstName, String lastName) {
        }
}
