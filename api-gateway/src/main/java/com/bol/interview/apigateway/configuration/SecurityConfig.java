package com.bol.interview.apigateway.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity) {

        return serverHttpSecurity.
                csrf(ServerHttpSecurity.CsrfSpec::disable).
                authorizeExchange(exchangeSpec ->
                        exchangeSpec.
                                anyExchange().
                                authenticated()
                ).oauth2ResourceServer(spec ->
                        spec.jwt(Customizer.withDefaults()))
                .build();
    }
}
