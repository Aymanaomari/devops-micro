package com.project.order.service.stubs;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class ProductClientStub {

    public static void stubProductPriceCall(String productId, String price) {
        stubFor(get(urlEqualTo("/api/product/" + productId + "/price")).willReturn(
                aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(price)));
    }

    public static void stubProductPriceCall(String productId, Double price) {
        stubProductPriceCall(productId, String.valueOf(price));
    }
}
