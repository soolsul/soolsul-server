package com.soolsul.soolsulserver.curation.common.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class CurationPostLookupResponse {
    private String content;
    private String userName; //사용자 이름
    private List<PostPhotoImageResponse> postImageUrls;
    private int userLikes;

    @QueryProjection
    public CurationPostLookupResponse(
            String content,
            String userName,
            List<PostPhotoImageResponse> postImageUrls,
            int userLikes
    ) {
        this.content = content;
        this.userName = userName;
        this.postImageUrls = postImageUrls;
        this.userLikes = userLikes;
    }

}
