package com.nrr.project.authserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("main")
    public String main() {
        return "This Is Main Page Of Authorization Server";
    }
}
