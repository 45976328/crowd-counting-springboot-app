package com.backend.api_gateway.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration // Marks this class as a Spring configuration class
@EnableWebFluxSecurity // Enables reactive (WebFlux-based) Spring Security
public class SecurityConfig {

    @Bean // Defines a bean for the security filter chain
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {

        // Configure reactive security for the API Gateway
        return http
            // Disable CSRF protection since this is a stateless API (no sessions or forms)
            .csrf(csrf -> csrf.disable())

            // Define route authorization rules
            .authorizeExchange(ex -> ex
                // Allow unrestricted access to Eureka endpoints (for service registration)
                .pathMatchers("/index.html", "/eureka/**", "/kafbat", "/keycloak", "/grafana", "/influxdb").permitAll()

                // Require authentication for any other route
                .anyExchange().authenticated()
            )

            // Configure OAuth2 Resource Server with JWT support (for Keycloak)
            // The JWT settings (issuer-uri, etc.) come from application.yml
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))

            // Build the configured security filter chain
            .build();
    }
}
