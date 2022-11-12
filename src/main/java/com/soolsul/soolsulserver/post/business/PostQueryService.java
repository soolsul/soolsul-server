package com.soolsul.soolsulserver.post.business;

import com.soolsul.soolsulserver.post.domain.Post;
import com.soolsul.soolsulserver.post.domain.PostPhoto;
import com.soolsul.soolsulserver.post.domain.PostRepository;
import com.soolsul.soolsulserver.post.exception.PostNotFoundException;
import com.soolsul.soolsulserver.post.presentation.dto.PostDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostQueryService {

    private final PostRepository postRepository;

    public PostDetailResponse findPostDetail(String loginUserId, String postId) {
        Post findPost = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
        // TODO : userDetailService에서 Post의 주인을 찾아와야함
        // TODO : Bar 정보 찾아와야 함, 아직 Bar 도메인 미구현

        List<String> urlList = convertImageUrlList(findPost);

        return PostDetailResponse.of(loginUserId, "tempUserName", findPost, urlList);
    }

    private List<String> convertImageUrlList(Post findPost) {
        return findPost.getPhotos()
                .stream()
                .map(PostPhoto::getUrl)
                .collect(Collectors.toList());
    }
}

