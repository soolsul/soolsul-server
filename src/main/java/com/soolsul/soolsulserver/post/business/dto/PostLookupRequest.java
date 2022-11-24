package com.soolsul.soolsulserver.post.business.dto;

import com.soolsul.soolsulserver.user.auth.repository.dto.UserLookUpResponse;
import com.soolsul.soolsulserver.post.domain.Post;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record PostLookupRequest(

        @NotBlank
        String userId,

        @NotBlank
        String postId,

        @NotNull
        Post post,

        @NotNull
        UserLookUpResponse findUser
) {
}
