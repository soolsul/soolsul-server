package com.soolsul.soolsulserver.post.presentation.dto;

import com.soolsul.soolsulserver.user.auth.repository.dto.UserLookUpResponse;
import com.soolsul.soolsulserver.bar.presentation.dto.BarLookupResponse;
import com.soolsul.soolsulserver.post.business.dto.response.PostDetailLikeResponse;
import com.soolsul.soolsulserver.post.business.dto.response.PostDetailStoreResponse;
import com.soolsul.soolsulserver.post.business.dto.response.PostDetailUserResponse;
import com.soolsul.soolsulserver.post.domain.Post;
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

    public PostDetailResponse(Post post, UserLookUpResponse user, BarLookupResponse bar, List<String> imageUrlList, boolean userClickedLike) {
        this(post.getId(), post.getScore(), post.getContents(),
                imageUrlList,
                new PostDetailLikeResponse(post.likeCount(), userClickedLike),
                new PostDetailUserResponse(user.userId(), user.nickName(), user.profileImage()),
                new PostDetailStoreResponse(bar.id(), bar.name(), bar.description()));
    }
}
