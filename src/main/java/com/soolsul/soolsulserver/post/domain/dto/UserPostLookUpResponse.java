package com.soolsul.soolsulserver.post.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;

public record UserPostLookUpResponse(
        @NotBlank String postId,
        @URL @NotBlank String imageUrl
) {
    @QueryProjection
    public UserPostLookUpResponse(@NotBlank String postId, @URL @NotBlank String imageUrl) {
        this.postId = postId;
        this.imageUrl = imageUrl;
    }
}
