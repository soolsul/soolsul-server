package com.soolsul.soolsulserver.user.mypage.presentation.dto.response;

import com.soolsul.soolsulserver.post.domain.dto.UserReplyLookUpResponse;

import java.util.List;

public record UserReplyListLookUpResponse(
        List<UserReplyLookUpResponse> replyList
) {
}
