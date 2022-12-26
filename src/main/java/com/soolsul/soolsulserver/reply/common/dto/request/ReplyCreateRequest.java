package com.soolsul.soolsulserver.reply.common.dto.request;

import javax.validation.constraints.NotBlank;

public record ReplyCreateRequest(
        @NotBlank String contents
) {
}
