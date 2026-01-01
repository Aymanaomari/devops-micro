package com.project.inventory.service.service;

import com.project.inventory.service.model.Inventory;
import com.project.inventory.service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service class for managing inventory operations.
 * This class provides methods to check stock availability and manage inventory.
 */
@Service
@RequiredArgsConstructor
public class InventoryService {
    final private InventoryRepository inventoryRepository;

    /**
     * Checks if a product with the given productId is in stock with the required
     * quantity.
     *
     * @param productId the product ID of the product
     * @param quantity  the quantity to check for availability
     * @return true if the product is in stock with the required quantity, false
     *         otherwise
     */
    public boolean isInStock(String productId, Integer quantity) {
        return inventoryRepository.existsByProductIdAndQuantityGreaterThanEqual(productId, quantity);
    }

    /**
     * Adds a product to the inventory with the specified SKU code and quantity.
     *
     * @param skuCode  the SKU code of the product
     * @param quantity the quantity to add to the inventory
     */
    public boolean addProductToInventory(String productId, String productName, Integer quantity) {
        var inventory = inventoryRepository.findByProductId(productId)
                .orElseGet(() -> {
                    var newInventory = new Inventory();
                    newInventory.setProductId(productId);
                    newInventory.setProductName(productName);
                    newInventory.setQuantity(0);
                    return newInventory;
                });
        inventory.setQuantity(inventory.getQuantity() + quantity);
        inventoryRepository.save(inventory);
        return true;
    }

}
