package com.soolsul.soolsulserver.reply.presentation.dto.response;

import com.soolsul.soolsulserver.reply.business.dto.response.ReplyDetailResponse;
import org.springframework.data.domain.Slice;

public record PostRepliesResponse(
        Slice<ReplyDetailResponse> replies
) {
}
