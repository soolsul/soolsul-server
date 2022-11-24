package com.soolsul.soolsulserver.user.mypage.presentation.dto.response;

import com.soolsul.soolsulserver.post.domain.dto.ScrapedPostLookUpResponse;

import java.util.List;

public record ScrapedPostListLookUpResponse(
        List<ScrapedPostLookUpResponse> postList
) {
}
