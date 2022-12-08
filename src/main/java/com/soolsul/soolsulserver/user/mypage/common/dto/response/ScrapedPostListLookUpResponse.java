package com.soolsul.soolsulserver.user.mypage.common.dto.response;

import com.soolsul.soolsulserver.post.common.dto.response.ScrapedPostLookUpResponse;

import java.util.List;

public record ScrapedPostListLookUpResponse(
        List<ScrapedPostLookUpResponse> postList
) {
}
