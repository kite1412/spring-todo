package com.nrr.project.resourceserver.util;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class ThrowUsernameNotFound {

    public static UsernameNotFoundException get() {
        return new UsernameNotFoundException("User Not Found");
    }
}
