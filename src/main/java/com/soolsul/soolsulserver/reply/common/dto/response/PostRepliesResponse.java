package com.soolsul.soolsulserver.reply.common.dto.response;

import org.springframework.data.domain.Slice;

public record PostRepliesResponse(
        Slice<ReplyDetailResponse> replies
) {
}
