package com.nrr.project.authserver.security.service;

import com.nrr.project.authserver.entity.User;
import com.nrr.project.authserver.repository.UserRepository;
import com.nrr.project.authserver.security.model.UD;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
public class UDS implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).
                orElseThrow(() -> new UsernameNotFoundException("Username Not Found"));
        return new UD(user);
    }
}
