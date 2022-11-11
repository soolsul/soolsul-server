package com.soolsul.soolsulserver.post.presentation.dto;

import com.soolsul.soolsulserver.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostDetailResponse {

    private String UserName;
    private Float Score;
    private List<String> ImageUrls;
    private String Contents;

    public static PostDetailResponse of(String userName, Post post, List<String> urlList) {
        return new PostDetailResponse(userName, post.getScore(), urlList, post.getContents());
    }
}
