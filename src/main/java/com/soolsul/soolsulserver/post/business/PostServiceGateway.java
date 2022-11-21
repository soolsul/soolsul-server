package com.soolsul.soolsulserver.post.business;

import com.soolsul.soolsulserver.location.request.LocationSquareRangeRequest;
import com.soolsul.soolsulserver.post.facade.PostQueryFacade;
import com.soolsul.soolsulserver.post.presentation.dto.PostCreateRequest;
import com.soolsul.soolsulserver.post.presentation.dto.PostDetailResponse;
import com.soolsul.soolsulserver.post.presentation.dto.PostListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceGateway implements CommandService<PostCreateRequest>, QueryService {

    private final PostCommandService postCommandService;
    private final PostQueryFacade postQueryFacade;

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
        return postQueryFacade.findPostDetail(userId, postId);
    }

    @Override
    public PostListResponse findAll(String userId, LocationSquareRangeRequest locationSquareRangeRequest, Pageable pageable) {
        return postQueryFacade.findAllPostByLocation(userId, locationSquareRangeRequest, pageable);
    }

}
