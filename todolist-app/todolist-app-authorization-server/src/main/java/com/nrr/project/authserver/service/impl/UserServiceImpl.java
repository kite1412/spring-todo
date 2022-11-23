package com.nrr.project.authserver.service.impl;

import com.nrr.project.authserver.dto.UserDTO;
import com.nrr.project.authserver.entity.Token;
import com.nrr.project.authserver.entity.User;
import com.nrr.project.authserver.repository.TokenRepository;
import com.nrr.project.authserver.repository.UserRepository;
import com.nrr.project.authserver.service.contract.UserService;
import com.nrr.project.authserver.util.DetailsHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final DetailsHolder detailsHolder = new DetailsHolder();
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           TokenRepository tokenRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String register(UserDTO user) {
        userRepository.save(createNewUser(user));
        Token generateNewToken = Token.builder()
                .token(UUID.randomUUID().toString())
                .build();
        Token token = tokenRepository.save(generateNewToken);
        detailsHolder.usernameHolder(userRepository, user.username());
        detailsHolder.tokenHolder(tokenRepository, token.getToken());
        return token.getToken();
    }

    private User createNewUser(UserDTO userData) {
        return User.builder()
                .username(userData.username())
                .password(passwordEncoder.encode(userData.password()))
                .role("USER")
                .build();
    }

    @Override
    public String registerAdmin(UserDTO user) {
        User newUser = createNewUser(user);
        newUser.setRole("ADMIN");
        userRepository.save(newUser);
        return "Admin Registration Success!";
    }

    @Override
    public String enableAccount(String token) {
        Optional<Token> byId = tokenRepository.findById(token);
        if (byId.isEmpty()) {
            return "Invalid Token";
        }
        Token autoDeletedToken = null;
        try {
            autoDeletedToken = byId.get();
            String username = detailsHolder.getUsername();
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User Not Found")
                    );
            user.setEnabled(true);
            userRepository.save(user);
        } finally {
            tokenRepository.delete(autoDeletedToken);
        }
        return "Success Activating Account!";
    }

    @Override
    public String resendToken() {
        String token = detailsHolder.getToken();
        Optional<Token> byId = tokenRepository.findById(token);
        if (byId.isEmpty()) {
            return "Token Expired";
        }
        return "Token: " + byId.get().getToken();
    }
}
