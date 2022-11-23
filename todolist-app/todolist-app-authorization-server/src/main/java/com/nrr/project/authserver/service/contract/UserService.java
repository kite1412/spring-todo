package com.nrr.project.authserver.service.contract;

import com.nrr.project.authserver.dto.UserDTO;

public interface UserService {

    String register(UserDTO user);

    String registerAdmin(UserDTO user);

    String enableAccount(String token);

    String resendToken();
}
