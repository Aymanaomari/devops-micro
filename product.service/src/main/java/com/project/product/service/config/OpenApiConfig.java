package com.project.product.service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI productServiceAPI() {
        return new OpenAPI().info(new Info().title("Product Service Api").
                description("This is the Rest API for Product Serivce").
                version("v0.0.1").
                license(new License().name("Apache 2.0")))                ;

    }
}
