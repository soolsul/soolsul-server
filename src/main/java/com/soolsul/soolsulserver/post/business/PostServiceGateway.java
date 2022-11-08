package com.soolsul.soolsulserver.post.business;

import com.soolsul.soolsulserver.auth.User;
import com.soolsul.soolsulserver.post.presentation.dto.PostCreateRequest;
import com.soolsul.soolsulserver.post.presentation.dto.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceGateway implements CommandService<PostCreateRequest>, QueryService<PostResponse> {

    private final PostCommandService postCommandService;

    @Override
    public void create(User user, PostCreateRequest request) {
        postCommandService.create(user, request);
    }

    @Override
    public void update(String id, PostCreateRequest param) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public PostResponse find(String id) {
        return null;
    }
}
