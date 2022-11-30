package com.soolsul.soolsulserver.post.domain.dto;

import com.querydsl.core.annotations.QueryProjection;

import javax.validation.constraints.NotBlank;

public record UserReplyLookUpResponse(
        @NotBlank String postId,
        @NotBlank String contents
) {
    @QueryProjection
    public UserReplyLookUpResponse(@NotBlank String postId, @NotBlank String contents) {
        this.postId = postId;
        this.contents = contents;
    }
}
