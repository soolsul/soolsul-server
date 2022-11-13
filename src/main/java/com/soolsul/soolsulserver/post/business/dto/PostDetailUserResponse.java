package com.soolsul.soolsulserver.post.business.dto;

import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record PostDetailUserResponse(

        @NotNull
        String userId,

        @NotNull
        @Size(min = 1, max = 30)
        String userNickname,

        @URL
        String userProfileUrl
) {
}
