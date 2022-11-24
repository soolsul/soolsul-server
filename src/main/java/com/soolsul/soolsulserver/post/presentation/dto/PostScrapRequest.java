package com.soolsul.soolsulserver.post.presentation.dto;

import javax.validation.constraints.NotBlank;

public record PostScrapRequest(

        @NotBlank
        String postId
) {
}
