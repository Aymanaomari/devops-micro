package com.project.order.service.service;

import com.project.order.service.client.InventoryClient;
import com.project.order.service.client.ProductClient;
import com.project.order.service.dto.OrderRequest;
import com.project.order.service.model.Order;
import com.project.order.service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class OrderService {

    final private OrderRepository orderRepository;
    final private InventoryClient inventoryClient;
    final private ProductClient productClient;
    final private Logger log = Logger.getLogger("logger");

    public void placeOrder(OrderRequest orderRequest) {

        var isProductInStock = inventoryClient.isInStock(orderRequest.productId(), orderRequest.quantity());

        if (!isProductInStock) {
            log.warning("Product with skuCode " + orderRequest.productId() + " is not in stock");
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Product with skuCode " + orderRequest.productId() + " is not in stock");
        }

        BigDecimal productPrice = productClient.getProductById(orderRequest.productId());

        if (productPrice == null) {
            log.warning("Could not retrieve price for productId " + orderRequest.productId());
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Could not retrieve price for productId " + orderRequest.productId());
        }

        BigDecimal totalPrice = calculateTotalPrice(productPrice, orderRequest.quantity());

        Order order = new Order();
        order.setProductId(orderRequest.productId());
        order.setProductName(orderRequest.productName());
        order.setQuantity(orderRequest.quantity());
        order.setPrice(totalPrice);

        orderRepository.save(order);

    }

    /**
     * Calculates the total price given price and quantity.
     */
    public static java.math.BigDecimal calculateTotalPrice(java.math.BigDecimal price, int quantity) {
        if (price == null)
            return java.math.BigDecimal.ZERO;
        return price.multiply(java.math.BigDecimal.valueOf(quantity));
    }
}
