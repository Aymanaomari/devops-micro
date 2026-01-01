package com.project.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
                httpSecurity.cors(cors -> cors.configurationSource(request -> {
                        org.springframework.web.cors.CorsConfiguration config = new org.springframework.web.cors.CorsConfiguration();
                        config.setAllowedOriginPatterns(
                                        java.util.List.of("http://localhost:4200", "http://localhost:8080"));
                        config.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                        config.setAllowedHeaders(java.util.List.of("*"));
                        config.setAllowCredentials(true);
                        config.setExposedHeaders(java.util.List.of("Authorization"));
                        return config;
                }));

                return httpSecurity
                                .authorizeHttpRequests(authorize -> authorize
                                                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**",
                                                                "/swagger-resources/**", "/webjars/**",
                                                                "/api-docs/**", "/aggregate/**",
                                                                "/actuator/health/**", "/actuator/health")
                                                .permitAll()
                                                .anyRequest().authenticated())
                                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                                .csrf(csrf -> csrf.disable())
                                .build();
        }
}