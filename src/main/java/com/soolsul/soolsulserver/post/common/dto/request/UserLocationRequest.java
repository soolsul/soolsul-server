package com.soolsul.soolsulserver.post.common.dto.request;

public record UserLocationRequest(
        double latitude,
        double longitude,
        int level
) {
}
