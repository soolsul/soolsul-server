package com.soolsul.soolsulserver.user.mypage.presentation.dto.response;

import com.soolsul.soolsulserver.post.domain.dto.UserPostLookUpResponse;

import java.util.List;

public record UserPostListLookUpResponse(
        List<UserPostLookUpResponse> postList
) {
}
