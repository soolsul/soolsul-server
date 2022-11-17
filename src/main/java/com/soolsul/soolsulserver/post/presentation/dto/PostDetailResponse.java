package com.soolsul.soolsulserver.post.presentation.dto;

import com.soolsul.soolsulserver.post.business.dto.PostDetailLikeResponse;
import com.soolsul.soolsulserver.post.business.dto.PostDetailStoreResponse;
import com.soolsul.soolsulserver.post.business.dto.PostDetailUserResponse;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;


public record PostDetailResponse(

        @NotBlank
        String postId,

        @NotNull
        @Pattern(regexp = "1-5")
        Float score,

        @NotBlank
        String contents,

        @URL
        @Size(max = 10)
        List<String> imageUrls,

        PostDetailLikeResponse like,
        PostDetailUserResponse user,
        PostDetailStoreResponse store
) {
}
