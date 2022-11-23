package com.nrr.project.authserver.controller;

import com.nrr.project.authserver.dto.UserDTO;
import com.nrr.project.authserver.service.contract.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("registration")
    public String registration(@RequestBody UserDTO user) {
        return userService.register(user);
    }

    @PostMapping(path = "registration", params = "token")
    public String enableAccount(Authentication authentication,
                                @RequestParam String token) {
        return userService.enableAccount(token);
    }

    @PostMapping(value = "admin_registration")
    public String adminRegistration(@RequestBody UserDTO user) {
        return userService.registerAdmin(user);
    }

    @GetMapping("resend_token")
    public String resendToken() {
        return userService.resendToken();
    }
}
