package com.project.order.service;

import com.project.order.service.stubs.InventoryClientStub;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.testcontainers.containers.MySQLContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
class OrderServiceApplicationTests {

    @ServiceConnection
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.3.0");

    @LocalServerPort
    private Integer port;

    static {
        mySQLContainer.start();
    }

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    void shouldSubmitOrder() {

        String submitOrderRequestJSON = """
                {
                    "skuCode": "iphone_15",
                    "price": 1000,
                    "quantity": 50
                }
                """;

        InventoryClientStub.stubInventoryCall("iphone_15", 50);

        String responseBody = RestAssured.given()
                .contentType("application/json")
                .body(submitOrderRequestJSON)
                .when()
                .post("/api/order")
                .then()
                .statusCode(201)
                .extract()
                .asString();

    }

}
