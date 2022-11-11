package com.soolsul.soolsulserver.post.business.dto;

import com.soolsul.soolsulserver.auth.User;
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
    public PostDetailUserResponse(User user) {
        this(user.getId(), user.getUsername(), "프로필 이미지 링크 들어가야함");
    }
}
