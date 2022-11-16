package com.soolsul.soolsulserver.post.presentation.dto;

public record UserLocationRequest(
        double latitude,
        double longitude,
        int level
) {
}
