package com.soolsul.soolsulserver.user.mypage.common.dto.response;

import com.soolsul.soolsulserver.post.common.dto.response.UserPostLookUpResponse;

import java.util.List;

public record UserPostListLookUpResponse(
        List<UserPostLookUpResponse> postList
) {
}
