package com.nrr.project.client.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private static final String[] WHITE_LIST = {
            "/todolist"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .oauth2Login(c -> {
                    c.loginPage(
                            OAuth2AuthorizationRequestRedirectFilter
                                    .DEFAULT_AUTHORIZATION_REQUEST_BASE_URI + "/app-client-openid"
                    );
                })
                .oauth2Client(Customizer.withDefaults())
                .cors().and().csrf().disable()
                .authorizeRequests(c -> {
                    c.mvcMatchers(HttpMethod.POST, WHITE_LIST).permitAll().
                            anyRequest().authenticated();
                })
                .build();
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(clientRegistration());
    }

    private ClientRegistration clientRegistration() {
        return ClientRegistration.withRegistrationId("app-client-openid")
                .clientId("client1")
                .clientSecret("secret")
                .clientName("app-client-openid")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .scope(OidcScopes.OPENID)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .redirectUri("http://my-app:9000/login/oauth2/code/{registrationId}")

                .authorizationUri("http://backend-auth:8080/oauth2/authorize")
                .userInfoUri("http://backend-auth:8080/userinfo")
                .jwkSetUri("http://backend-auth:8080/oauth2/jwks")
                .issuerUri("http://backend-auth:8080")
                .tokenUri("http://backend-auth:8080/oauth2/token")

                .build();
    }
}
