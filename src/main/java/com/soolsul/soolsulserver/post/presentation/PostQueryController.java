package com.soolsul.soolsulserver.post.presentation;

import com.soolsul.soolsulserver.auth.User;
import com.soolsul.soolsulserver.common.response.BaseResponse;
import com.soolsul.soolsulserver.common.response.ResponseCodeAndMessages;
import com.soolsul.soolsulserver.post.business.PostServiceGateway;
import com.soolsul.soolsulserver.post.presentation.dto.PostDetailResponse;
import com.soolsul.soolsulserver.post.presentation.dto.PostListResponse;
import com.soolsul.soolsulserver.post.presentation.dto.UserLocationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostQueryController {

    private final PostServiceGateway postServiceGateway;

    @GetMapping("/{postId}")
    public ResponseEntity<BaseResponse<PostDetailResponse>> findDetailPost(@PathVariable String postId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        PostDetailResponse postDetailResponse = postServiceGateway.find(user.getId(), postId);
        return new ResponseEntity<>(new BaseResponse<>(ResponseCodeAndMessages.FEED_FIND_SUCCESS, postDetailResponse), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<BaseResponse<PostListResponse>> findAllDetailPost(UserLocationRequest locationRequest, Pageable pageable, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        PostListResponse postListResponse = postServiceGateway.findAll(user.getId(), locationRequest, pageable);
        return new ResponseEntity<>(new BaseResponse<>(ResponseCodeAndMessages.FEED_FIND_ALL_SUCCESS, postListResponse), HttpStatus.OK);
    }
}
