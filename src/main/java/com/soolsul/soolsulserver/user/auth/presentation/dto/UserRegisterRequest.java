package com.soolsul.soolsulserver.user.auth.presentation.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public record UserRegisterRequest(

        @Email @NotBlank String email,
        @NotBlank String password,
        @NotBlank String phone,
        @NotBlank String name,
        @NotBlank String nickname
) {
}
