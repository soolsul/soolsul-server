package com.soolsul.soolsulserver.reply.presentation.dto.request;

import javax.validation.constraints.NotBlank;

public record PostReplyRequest(

        @NotBlank
        String contents
) {
}
