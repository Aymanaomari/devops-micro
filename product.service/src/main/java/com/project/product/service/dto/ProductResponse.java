package com.project.product.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(description = "Response object for product details")
public record ProductResponse(

        @Schema(description = "Unique identifier of the product", example = "12345") String id,

        @Schema(description = "Name of the product", example = "Laptop") String name,

        @Schema(description = "Description of the product", example = "A high-performance laptop") String description,

        @Schema(description = "Price of the product", example = "999.99") BigDecimal price) {
}
