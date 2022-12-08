package com.soolsul.soolsulserver.user.mypage.common.dto.response;

import com.soolsul.soolsulserver.post.common.dto.response.UserReplyLookUpResponse;

import java.util.List;

public record UserReplyListLookUpResponse(
        List<UserReplyLookUpResponse> replyList
) {
}
