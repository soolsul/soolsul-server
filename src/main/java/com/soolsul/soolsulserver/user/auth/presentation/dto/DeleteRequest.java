package com.soolsul.soolsulserver.user.auth.presentation.dto;

import javax.validation.constraints.NotBlank;

public record DeleteRequest(
        @NotBlank String userId
) {
}
