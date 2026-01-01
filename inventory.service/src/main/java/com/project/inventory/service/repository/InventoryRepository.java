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
     * Checks if an inventory item exists with the given product ID and has quantity
     * greater than or equal to the specified amount.
     *
     * @param productId the product ID of the item
     * @param quantity  the minimum quantity required
     * @return true if the item exists with sufficient quantity, false otherwise
     */
    boolean existsByProductIdAndQuantityGreaterThanEqual(String productId, Integer quantity);

    /**
     * Finds an inventory item by its product ID.
     *
     * @param productId the product ID of the item
     * @return an Optional containing the inventory item if found, empty otherwise
     */
    Optional<Inventory> findByProductId(String productId);
}
