package com.soolsul.soolsulserver.post.presentation;

import com.soolsul.soolsulserver.user.auth.annotation.CurrentUser;
import com.soolsul.soolsulserver.user.auth.domain.CustomUser;
import com.soolsul.soolsulserver.common.response.BaseResponse;
import com.soolsul.soolsulserver.common.response.ResponseCodeAndMessages;
import com.soolsul.soolsulserver.location.common.dto.request.LocationSquareRangeRequest;
import com.soolsul.soolsulserver.post.facade.PostFacadeGateway;
import com.soolsul.soolsulserver.post.common.dto.request.PostCreateRequest;
import com.soolsul.soolsulserver.post.common.dto.response.PostDetailResponse;
import com.soolsul.soolsulserver.post.common.dto.response.PostListResponse;
import com.soolsul.soolsulserver.post.common.dto.request.PostScrapRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostFacadeGateway postFacadeGateway;

    @PostMapping
    public ResponseEntity<BaseResponse<Void>> createPost(
            @Valid @RequestBody PostCreateRequest request,
            @CurrentUser CustomUser currentUser
    ) {
        postFacadeGateway.create(currentUser.getId(), request);
        return ResponseEntity.ok(new BaseResponse<>(ResponseCodeAndMessages.FEED_CREATE_SUCCESS, null));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<BaseResponse<PostDetailResponse>> findDetailPost(
            @PathVariable String postId,
            @CurrentUser CustomUser currentUser
    ) {
        PostDetailResponse postDetailResponse = postFacadeGateway.find(currentUser.getId(), postId);
        return ResponseEntity.ok(new BaseResponse<>(ResponseCodeAndMessages.FEED_FIND_SUCCESS, postDetailResponse));
    }

    @GetMapping
    public ResponseEntity<BaseResponse<PostListResponse>> findAllDetailPostByLocation(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam(defaultValue = "3") int level,
            @PageableDefault(size = 6) Pageable pageable,
            @CurrentUser CustomUser currentUser
    ) {
        LocationSquareRangeRequest locationSquareRangeRequest = new LocationSquareRangeRequest(latitude, longitude, level);
        PostListResponse postListResponse = postFacadeGateway.findAll(currentUser.getId(), locationSquareRangeRequest, pageable);
        return ResponseEntity.ok(new BaseResponse<>(ResponseCodeAndMessages.FEED_FIND_ALL_SUCCESS, postListResponse));
    }

    @PostMapping("/scraps")
    public ResponseEntity<BaseResponse<Void>> scrapPost(
            @Valid @RequestBody PostScrapRequest request,
            @CurrentUser CustomUser currentUser
    ) {
        postFacadeGateway.scrap(currentUser.getId(), request.postId());
        return ResponseEntity.ok(new BaseResponse<>(ResponseCodeAndMessages.FEED_SCRAP_SUCCESS, null));
    }
}
