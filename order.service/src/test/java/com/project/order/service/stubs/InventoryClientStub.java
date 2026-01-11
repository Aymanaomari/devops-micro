package com.project.order.service.stubs;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class InventoryClientStub {

    public static void stubInventoryCall(String productId, Integer quantity) {
        stubFor(get(urlEqualTo("/api/inventory?productId=" + productId + "&quantity=" + quantity)).willReturn(
                aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody("true")));
    }
}
