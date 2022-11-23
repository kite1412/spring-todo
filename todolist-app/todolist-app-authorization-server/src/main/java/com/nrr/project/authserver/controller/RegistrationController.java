package com.nrr.project.authserver.controller;

import com.nrr.project.authserver.dto.UserDTO;
import com.nrr.project.authserver.entity.Token;
import com.nrr.project.authserver.entity.User;
import com.nrr.project.authserver.service.contract.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private String token;
    private final UserService userService;

    @GetMapping("registration")
    public String getRegistrationForm(User user) {
        return "registration";
    }

    @PostMapping("register")
    public String registration(Model model, @Valid UserDTO user) {
        String getToken = userService.register(user);
        token = getToken;
        return "redirect:/registered";
    }

    @GetMapping("registered")
    public String successRegistration(Model model) {
        model.addAttribute("token", token);
        return "registered";
    }

    @GetMapping("activate")
    public String enableAccountForm(Token token) {
        return "send-token";
    }

    // TODO: fail activating account handling
    @PostMapping("token_verification")
    public String sendToken(String token) {
        userService.enableAccount(token);
        return"redirect:/registration_complete";
    }

    // TODO: auto-authenticated by spring
    @GetMapping("registration_complete")
    public String registrationComplete() {
        return "complete";
    }
}
