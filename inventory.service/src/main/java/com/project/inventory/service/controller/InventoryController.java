package com.project.inventory.service.controller;

import com.project.inventory.service.service.InventoryService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling inventory-related operations.
 * This controller provides endpoints to check inventory stock.
 */
@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    final private InventoryService inventoryService;

    /**
     * Checks if a specific product is available in the inventory with the required
     * quantity.
     *
     * @param productId the unique identifier of the product to check
     * @param quantity  the quantity to verify for availability
     * @return true if the product is available in the specified quantity, false
     *         otherwise
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@RequestParam String productId, @RequestParam Integer quantity) {
        return inventoryService.isInStock(productId, quantity);
    }

    /**
     * Adds a new product to the inventory with the specified details.
     *
     * @param productId   the unique identifier of the product
     * @param productName the name of the product
     * @param quantity    the quantity to add to the inventory
     * @return true if the product was successfully added, false otherwise
     */
    @Operation(summary = "Add a product to inventory", description = "Adds a new product to the inventory with specified details.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public boolean addProductToInventory(@RequestParam String productId, @RequestParam String productName,
            @RequestParam Integer quantity) {
        return inventoryService.addProductToInventory(productId, productName, quantity);
    }
}
