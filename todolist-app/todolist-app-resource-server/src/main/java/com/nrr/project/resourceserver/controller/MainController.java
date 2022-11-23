package com.nrr.project.resourceserver.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("main")
    public String main(Authentication authentication) {
        return "This Is Main Page Of Resource Server" + authentication.getName();
    }
}
