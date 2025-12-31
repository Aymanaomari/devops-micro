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
     * Checks if a specific SKU is in stock with the required quantity.
     *
     * @param skuCode  the SKU code of the product
     * @param quantity the quantity to check for availability
     * @return true if the product is in stock with the required quantity, false
     *         otherwise
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@RequestParam String skuCode, @RequestParam Integer quantity) {
        return inventoryService.isInStock(skuCode, quantity);
    }

    /**
     * Add A product to inventory
     * 
     * @param skuCode  the SKU code of the product
     * @param quantity the quantity to add to inventory
     */
    @Operation(summary = "Add a product to inventory", description = "Adds a new product to the inventory with specified details.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public boolean addProductToInventory(@RequestParam String skuCode, @RequestParam Integer quantity) {
        return inventoryService.addProductToInventory(skuCode, quantity);

    }
}
