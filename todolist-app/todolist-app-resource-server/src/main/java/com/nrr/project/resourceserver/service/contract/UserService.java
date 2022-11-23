package com.nrr.project.resourceserver.service.contract;

import org.springframework.security.core.Authentication;

public interface UserService {

    String autoRegisterUser(Authentication authenticatedUser);
}
