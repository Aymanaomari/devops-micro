package com.project.product.service.service;

import com.project.product.service.client.InventoryClient;
import com.project.product.service.dto.ProductRequest;
import com.project.product.service.dto.ProductResponse;
import com.project.product.service.exception.ProductNotFoundException;
import com.project.product.service.model.Product;
import com.project.product.service.repository.ProductRepostiory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

  final private InventoryClient inventoryClient;
  private final ProductRepostiory productRepository;

  /**
   * Creates a new product based on the provided product request.
   *
   * @param productRequest the product request containing product details
   * @return the created product response
   */
  public ProductResponse createProduct(ProductRequest productRequest) {
    Product product = Product.builder()
        .name(productRequest.name())
        .description(productRequest.description()) // fixed
        .price(productRequest.price())
        .build();

    productRepository.save(product);
    log.info("Product created successfully with id: {}", product.getId());

    inventoryClient.addToInventory(product.getId(), product.getName(), productRequest.quantity());

    return new ProductResponse(
        product.getId(),
        product.getName(),
        product.getDescription(),
        product.getPrice());
  }

  /**
   * Retrieves all products from the repository.
   *
   * @return a list of product responses
   */
  public List<ProductResponse> getAllProducts() {
    List<ProductResponse> products = productRepository.findAll().stream()
        .map((product) -> new ProductResponse(product.getId(), product.getName(), product.getDescription(),
            product.getPrice()))
        .toList();
    return products;
  }

  /**
   * Retrieves a product by its unique identifier.
   *
   * @param id the unique identifier of the product
   * @return the product response for the specified id
   * @throws ProductNotFoundException if the product is not found
   */
  public ProductResponse getProductById(String id) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
    return new ProductResponse(
        product.getId(),
        product.getName(),
        product.getDescription(),
        product.getPrice());
  }

  /**
   * Deletes a product by its unique identifier.
   *
   * @param id the unique identifier of the product to be deleted
   * @throws ProductNotFoundException if the product is not found
   */
  public void deleteProduct(String id) {
    if (!productRepository.existsById(id)) {
      throw new ProductNotFoundException("Product not found with id: " + id);
    }
    productRepository.deleteById(id);
    log.info("Product deleted successfully with id: {}", id);
  }

  /**
   * Retrieves the price of a product by its unique identifier.
   *
   * @param id the unique identifier of the product
   * @return the price of the specified product
   * @throws ProductNotFoundException if the product is not found
   */
  public BigDecimal getProductPriceById(String id) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
    return product.getPrice();
  }
}
