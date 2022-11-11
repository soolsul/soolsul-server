package com.soolsul.soolsulserver.post.presentation.dto;

import com.soolsul.soolsulserver.post.business.dto.PostDetailLikeResponse;
import com.soolsul.soolsulserver.post.domain.Post;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;


public record PostDetailResponse(

        @NotNull
        String UserName,

        @NotNull
        @Pattern(regexp = "1-5")
        Float Score,

        @NotBlank
        String Contents,

        @URL
        @Size(max = 10)
        List<String> ImageUrls,

        PostDetailLikeResponse likeInfo
) {

    public static PostDetailResponse of(String loginUserId, String userName, Post post, List<String> urlList) {
        boolean likeStatus = !Objects.isNull(loginUserId) && post.isLikeContain(loginUserId);
        return new PostDetailResponse(
                userName,
                post.getScore(),
                post.getContents(),
                urlList,
                new PostDetailLikeResponse(post.likeCount(), likeStatus)
        );
    }
}
