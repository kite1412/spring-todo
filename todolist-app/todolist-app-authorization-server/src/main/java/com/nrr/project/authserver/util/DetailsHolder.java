package com.nrr.project.authserver.util;

import com.nrr.project.authserver.entity.Token;
import com.nrr.project.authserver.entity.User;
import com.nrr.project.authserver.repository.TokenRepository;
import com.nrr.project.authserver.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class DetailsHolder {

    private String usernameHolder;
    private String tokenHolder;

    public void usernameHolder(UserRepository userRepository, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found")
                );
        usernameHolder = user.getUsername();
    }

    public void tokenHolder(TokenRepository tokenRepository, String token) {
        Token token1 = tokenRepository.findById(token)
                .orElseThrow(() -> new RuntimeException("Token Not Found"));
        tokenHolder = token1.getToken();
    }

    public String getUsername() {
        return usernameHolder;
    }

    public String getToken() {
        return tokenHolder;
    }
}
