package com.nrr.project.client.controller;

import com.nrr.project.client.dto.TodoTDO;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@AllArgsConstructor
public class TodoController {

    private static final String AUTHORIZED_CLIENT = "app-client-openid";
    private final WebClient webClient;

    @GetMapping("todolist")
    public String showTodos(@RegisteredOAuth2AuthorizedClient
                                        (registrationId = AUTHORIZED_CLIENT)
                            OAuth2AuthorizedClient authorizedClient) {
        return webClient.get()
                .uri("http://backend-resource:8090/todolist")
                .attributes(ServerOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient(authorizedClient))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @PostMapping("add")
    public String addTodo(@RegisteredOAuth2AuthorizedClient
                                      (registrationId = AUTHORIZED_CLIENT)
                          OAuth2AuthorizedClient authorizedClient,
                          String todo) {

        return webClient.post()
                .uri("http://backend-resource:8090/add")
                .body(BodyInserters.fromValue(todo))
                .attributes(ServerOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient(authorizedClient))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
