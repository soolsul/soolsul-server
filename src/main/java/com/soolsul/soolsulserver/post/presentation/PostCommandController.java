package com.soolsul.soolsulserver.post.presentation;

import com.soolsul.soolsulserver.auth.User;
import com.soolsul.soolsulserver.common.response.BaseResponse;
import com.soolsul.soolsulserver.common.response.ResponseCodeAndMessages;
import com.soolsul.soolsulserver.post.business.PostServiceGateway;
import com.soolsul.soolsulserver.post.presentation.dto.PostCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostCommandController {

    private final PostServiceGateway postServiceGateway;

    @PostMapping
    public ResponseEntity<BaseResponse<Void>> createPost(@RequestBody PostCreateRequest request, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        postServiceGateway.create(user.getId(), request);
        return new ResponseEntity<>(new BaseResponse<>(ResponseCodeAndMessages.FEED_CREATE_SUCCESS, null), HttpStatus.OK);
    }
}
