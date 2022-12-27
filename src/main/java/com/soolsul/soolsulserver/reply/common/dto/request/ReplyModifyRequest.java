package com.soolsul.soolsulserver.reply.common.dto.request;

import javax.validation.constraints.NotBlank;

public record ReplyModifyRequest(
        @NotBlank String contents
) {
}
