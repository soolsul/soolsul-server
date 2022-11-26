package com.soolsul.soolsulserver.user.auth.presentation.dto;

import javax.validation.constraints.NotBlank;

public record UserDeleteRequest(
        @NotBlank String userId
) {
}
