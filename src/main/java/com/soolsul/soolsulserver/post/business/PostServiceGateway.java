package com.soolsul.soolsulserver.post.business;

import com.soolsul.soolsulserver.post.presentation.dto.PostCreateRequest;
import com.soolsul.soolsulserver.post.presentation.dto.PostDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceGateway implements CommandService<PostCreateRequest>, QueryService<PostDetailResponse> {

    private final PostCommandService postCommandService;
    private final PostQueryService postQueryService;

    @Override
    public void create(String id, PostCreateRequest request) {
        postCommandService.create(id, request);
    }

    @Override
    public void update(String id, PostCreateRequest param) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public PostDetailResponse find(String id) {
        return null;
    }
}
