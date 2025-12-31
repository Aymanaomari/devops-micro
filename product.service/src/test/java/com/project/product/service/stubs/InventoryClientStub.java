package com.project.product.service.stubs;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class InventoryClientStub {
    
    public static void stubAddToInventoryCallForAnyProduct(Integer quantity, boolean response) {
        stubFor(post(urlPathEqualTo("/api/inventory"))
                .withQueryParam("skuCode", matching(".*")) // Match any skuCode (product ID)
                .withQueryParam("quantity", equalTo(String.valueOf(quantity)))
                .willReturn(
                        aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "application/json")
                                .withBody(String.valueOf(response))));
    }
    
}