package com.project.inventory.service.repository;

import com.project.inventory.service.model.Inventory;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing Inventory entities.
 * Provides methods for checking stock availability and retrieving inventory
 * data.
 */
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    /**
     * Checks if an inventory item exists with the given SKU code and has quantity
     * greater than or equal to the specified amount.
     *
     * @param SkuCode  the SKU code of the product
     * @param quantity the minimum quantity required
     * @return true if the item exists with sufficient quantity, false otherwise
     */
    boolean existsBySkuCodeAndQuantityIsGreaterThanEqual(String SkuCode, Integer quantity);

    /**
     * Finds an inventory item by its SKU code.
     *
     * @param skuCode the SKU code of the product
     * @return an Optional containing the inventory item if found, empty otherwise
     */
    Optional<Inventory> findBySkuCode(String skuCode);
}
