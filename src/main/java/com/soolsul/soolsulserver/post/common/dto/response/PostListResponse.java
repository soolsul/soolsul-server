package com.soolsul.soolsulserver.post.common.dto.response;

import java.util.List;

public record PostListResponse(
        List<PostDetailResponse> postList
) {
}
