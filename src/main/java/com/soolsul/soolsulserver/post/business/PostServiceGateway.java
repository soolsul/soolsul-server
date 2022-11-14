package com.soolsul.soolsulserver.post.business;

import com.soolsul.soolsulserver.post.presentation.dto.PostCreateRequest;
import com.soolsul.soolsulserver.post.presentation.dto.PostDetailResponse;
import com.soolsul.soolsulserver.post.presentation.dto.PostListResponse;
import com.soolsul.soolsulserver.post.presentation.dto.UserLocationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceGateway implements CommandService<PostCreateRequest>, QueryService {

    private final PostCommandService postCommandService;
    private final PostQueryService postQueryService;

    @Override
    public void create(String userId, PostCreateRequest request) {
        postCommandService.create(userId, request);
    }

    @Override
    public void update(String id, PostCreateRequest param) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public PostDetailResponse find(String userId, String postId) {
        return postQueryService.findPostDetail(userId, postId);
    }

    @Override
    public PostListResponse findAll(String userId, UserLocationRequest locationRequest, Pageable pageable) {
        return postQueryService.findAllPostByLocation(userId, locationRequest, pageable);
    }
}
