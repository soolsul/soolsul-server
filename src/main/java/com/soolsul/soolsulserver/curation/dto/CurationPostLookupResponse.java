package com.soolsul.soolsulserver.curation.dto;

import java.util.List;

public record CurationPostLookupResponse(
        String postTitle,
        String content,
        List<String> postImageUrls,
        int likes
) {
}
