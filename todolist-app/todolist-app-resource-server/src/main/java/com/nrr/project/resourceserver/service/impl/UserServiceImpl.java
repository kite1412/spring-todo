package com.nrr.project.resourceserver.service.impl;

import com.nrr.project.resourceserver.entity.User;
import com.nrr.project.resourceserver.repository.UserRepository;
import com.nrr.project.resourceserver.service.contract.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public String autoRegisterUser(Authentication authenticatedUser) {
        String username = authenticatedUser.getName();
        if (userRepository.findById(username).isEmpty()) {
            User newUser = User.builder()
                    .username(username)
                    .build();
            userRepository.save(newUser);
            log.info("Success auto-register user");
        }
        return "Success auto-register user";
    }
}
