package com.soolsul.soolsulserver.curation.common.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class PostPhotoImageResponse{

    private String postPhotoImageUrl;

    @QueryProjection
    public PostPhotoImageResponse(String postPhotoImageUrl) {
        this.postPhotoImageUrl = postPhotoImageUrl;
    }

}
