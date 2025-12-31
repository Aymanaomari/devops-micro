package com.project.inventory.service;

import io.restassured.RestAssured;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InventoryServiceApplicationTests {

    @ServiceConnection
    static MySQLContainer<?> mySQLContainer =
            new MySQLContainer<>("mysql:8.3.0");

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
    void shouldReadInventory() {

        Boolean response =
                RestAssured.given()
                        .when()
                        .get("/api/inventory?skuCode=iphone_15&quantity=1")
                        .then()
                        .statusCode(200)
                        .extract()
                        .as(Boolean.class);

        Boolean negativeResponse =
                RestAssured.given()
                        .when()
                        .get("/api/inventory?skuCode=iphone_15&quantity=2000")
                        .then()
                        .statusCode(200)
                        .extract()
                        .as(Boolean.class);

        Assertions.assertThat(response).isTrue();
        Assertions.assertThat(negativeResponse).isFalse();
    }
}
