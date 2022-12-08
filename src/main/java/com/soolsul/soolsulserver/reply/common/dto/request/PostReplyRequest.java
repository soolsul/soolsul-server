package com.soolsul.soolsulserver.reply.common.dto.request;

import javax.validation.constraints.NotBlank;

public record PostReplyRequest(
        @NotBlank String contents
) {
}
