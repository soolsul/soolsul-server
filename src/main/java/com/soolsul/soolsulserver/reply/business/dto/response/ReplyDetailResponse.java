package com.soolsul.soolsulserver.reply.business.dto.response;

import com.querydsl.core.annotations.QueryProjection;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record ReplyDetailResponse(
        @NotBlank String replyId,
        @NotBlank @Size(min = 1, max = 500) String contents,
        @NotBlank String userId,
        @NotBlank String nickName,
        @NotBlank String profileUrl
) {
    @QueryProjection
    public ReplyDetailResponse(
            @NotBlank String replyId,
            @NotBlank @Size(min = 1, max = 500) String contents,
            @NotBlank String userId,
            @NotBlank String nickName,
            @NotBlank String profileUrl) {
        this.replyId = replyId;
        this.contents = contents;
        this.userId = userId;
        this.nickName = nickName;
        this.profileUrl = profileUrl;
    }
}
