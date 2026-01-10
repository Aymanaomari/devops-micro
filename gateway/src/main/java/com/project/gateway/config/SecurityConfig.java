package com.project.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

        private final String[] freeResourceUrls = { "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**",
                        "/swagger-resources/**", "/api-docs/**", "/aggregate/**", "/actuator/prometheus" };

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
                return httpSecurity
                                .authorizeHttpRequests(authorize -> authorize
                                                .requestMatchers(freeResourceUrls).permitAll()
                                                .anyRequest().authenticated())
                                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                                .csrf(csrf -> csrf.disable())
                                .build();
        }

        @Bean
        CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(List.of(
                                "http://localhost:4200", // Angular frontend
                                "http://localhost:9000", // Gateway
                                "http://product-service:8080", // Product service
                                "http://order-service:8081", // Order service
                                "http://invertory-service:8082", // Inventory service
                                "http://keycloak:8080" // Keycloak
                ));
                configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));
                configuration.setExposedHeaders(List.of("Authorization")); // Expose Authorization header
                configuration.setAllowCredentials(true); // Allow cookies and credentials
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                return source;
        }
}