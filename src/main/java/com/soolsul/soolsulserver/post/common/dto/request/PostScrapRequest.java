package com.soolsul.soolsulserver.post.common.dto.request;

import javax.validation.constraints.NotBlank;

public record PostScrapRequest(

        @NotBlank
        String postId
) {
}
