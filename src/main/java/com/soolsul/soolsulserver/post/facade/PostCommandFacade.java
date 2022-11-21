package com.soolsul.soolsulserver.post.facade;

import com.soolsul.soolsulserver.post.business.PostCommandService;
import com.soolsul.soolsulserver.post.presentation.dto.PostCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class PostCommandFacade {

    private final PostCommandService postCommandService;

    public void create(String userId, PostCreateRequest request) {
        postCommandService.create(userId, request);
    }
}
