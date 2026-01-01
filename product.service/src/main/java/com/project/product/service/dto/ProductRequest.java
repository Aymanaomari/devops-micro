package com.project.product.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(description = "Request object for creating or updating a product")
public record ProductRequest(

        @Schema(description = "Name of the product", example = "Laptop") String name,

        @Schema(description = "Description of the product", example = "A high-performance laptop") String description,

        @Schema(description = "Price of the product", example = "999.99") BigDecimal price,

        @Schema(description = "Quantity of the product in stock", example = "10") Integer quantity) {
}
