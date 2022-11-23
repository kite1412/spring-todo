package com.nrr.project.resourceserver.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {
        return http
                .oauth2ResourceServer(oauth2Configure -> {
                    oauth2Configure.jwt()
                            .jwkSetUri("http://backend-auth:8080/oauth2/jwks");
                })
                .authorizeRequests(p -> {
                    p.anyRequest().authenticated();
                })
                .build();
    }
}
