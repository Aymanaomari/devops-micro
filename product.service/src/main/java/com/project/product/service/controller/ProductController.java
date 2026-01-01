package com.project.product.service.controller;

import com.project.product.service.dto.ProductRequest;
import com.project.product.service.dto.ProductResponse;
import com.project.product.service.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Controller for managing product-related operations.
 */
@Tag(name = "Product", description = "Endpoints for managing products")
@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    final private ProductService productService;

    /**
     * Test endpoint to verify the service is running.
     *
     * @return a simple test message with service status
     */
    @Operation(summary = "Test endpoint", description = "Simple endpoint to test if the product service is running.")
    @GetMapping("/test")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> testEndpoint() {
        return Map.of(
                "status", "success",
                "message", "Product service is running!",
                "timestamp", java.time.LocalDateTime.now().toString(),
                "service", "product-service");
    }

    /**
     * Creates a new product.
     *
     * @param request the product request containing product details
     * @return the created product response
     */
    @Operation(summary = "Create a new product", description = "Creates a new product with the provided details.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@RequestBody ProductRequest request) {
        return productService.createProduct(request);
    }

    /**
     * Retrieves all products.
     *
     * @return a list of product responses
     */
    @Operation(summary = "Get all products", description = "Retrieves a list of all available products.")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllPducts() {
        return productService.getAllProducts();
    }

    /**
     * Retrieves a product by its ID.
     * 
     * @param id Product ID
     * @return ProductResponse object
     */

    @Operation(summary = "Get product by ID", description = "Retrieves a product by its unique identifier.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable String id) {
        return productService.getProductById(id);
    }

    /**
     * Deletes a product by its ID.
     *
     * @param id Product ID
     */
    @Operation(summary = "Delete product by ID", description = "Deletes a product by its unique identifier.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
    }

    /**
     * Retrieves the price of a product by its ID.
     *
     * @param id Product ID
     * @return Map containing the product ID and its price
     */
    @Operation(summary = "Get product price by ID", description = "Returns the price of a product by its unique identifier.")
    @GetMapping("/{id}/price")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal getProductPriceById(@PathVariable String id) {
        return productService.getProductPriceById(id);

    }

}
