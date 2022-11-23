package com.nrr.project.authserver.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record UserDTO(
        @NotNull
                @NotBlank
        String username,
        @NotNull
                @NotBlank
        String password
) {}
