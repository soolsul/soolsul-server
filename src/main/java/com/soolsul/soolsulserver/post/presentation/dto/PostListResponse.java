package com.soolsul.soolsulserver.post.presentation.dto;

import java.util.List;

public record PostListResponse(
        List<PostDetailResponse> postList
) {
}
