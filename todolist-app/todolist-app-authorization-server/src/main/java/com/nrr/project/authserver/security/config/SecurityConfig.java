package com.nrr.project.authserver.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private static final String[] WHITE_LIST = {
            "/registration",
            "/register",
            "/registered",
            "/activate",
            "/token_verification",
            "/registration_complete",
            "/admin_registration",
            "/resend_token"
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {
        return http
                .formLogin(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeRequests(h -> {
                    h.mvcMatchers(WHITE_LIST).permitAll()
                            .mvcMatchers("/main").hasRole("USER")
                            .anyRequest().authenticated();
                })
                .build();
    }
}
