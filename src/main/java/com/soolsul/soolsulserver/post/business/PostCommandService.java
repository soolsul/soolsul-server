package com.soolsul.soolsulserver.post.business;

import com.soolsul.soolsulserver.user.auth.exception.UserNotFoundException;
import com.soolsul.soolsulserver.bar.exception.BarNotFoundException;
import com.soolsul.soolsulserver.bar.persistence.BarQueryRepository;
import com.soolsul.soolsulserver.bar.presentation.dto.BarLookupResponse;
import com.soolsul.soolsulserver.post.domain.Post;
import com.soolsul.soolsulserver.post.domain.PostPhoto;
import com.soolsul.soolsulserver.post.domain.PostRepository;
import com.soolsul.soolsulserver.post.domain.PostScrap;
import com.soolsul.soolsulserver.post.domain.PostScrapRepository;
import com.soolsul.soolsulserver.post.presentation.dto.PostCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostCommandService {

    private final PostRepository postRepository;
    private final PostScrapRepository postScrapRepository;
    private final BarQueryRepository barQueryRepository;

    public void create(String userId, PostCreateRequest request) {
        if (isInvalidUserId(userId)) {
            throw new UserNotFoundException();
        }

        BarLookupResponse barLookupResponse = barQueryRepository.findById(request.getBarId())
                .orElseThrow(BarNotFoundException::new);

        Post newPost = new Post(userId,
                barLookupResponse.id(),
                request.getScore(),
                request.getPostContent());

        newPost.addPhotoList(convertPhotoList(request, barLookupResponse));

        postRepository.save(newPost);
    }

    private List<PostPhoto> convertPhotoList(PostCreateRequest request, BarLookupResponse findBar) {
        return request.getImages()
                .stream()
                .map(url -> new PostPhoto(findBar.id(), "origin", url, "."))
                .collect(Collectors.toList());
    }

    private boolean isInvalidUserId(String userId) {
        return !StringUtils.hasText(userId);
    }

    public void scrap(String userId, String postId) {
        postScrapRepository.save(new PostScrap(userId, postId));
    }
}
