package com.soolsul.soolsulserver.post.presentation;

import com.soolsul.soolsulserver.common.response.BaseResponse;
import com.soolsul.soolsulserver.common.response.ResponseCodeAndMessages;
import com.soolsul.soolsulserver.location.common.dto.request.LocationSquareRangeRequest;
import com.soolsul.soolsulserver.post.common.dto.request.PostCreateRequest;
import com.soolsul.soolsulserver.post.common.dto.request.PostScrapRequest;
import com.soolsul.soolsulserver.post.common.dto.response.PostDetailResponse;
import com.soolsul.soolsulserver.post.common.dto.response.PostListResponse;
import com.soolsul.soolsulserver.post.facade.PostFacadeGateway;
import com.soolsul.soolsulserver.user.auth.annotation.CurrentUser;
import com.soolsul.soolsulserver.user.auth.vo.CurrentUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
            @CurrentUser CurrentUserDto currentUserDto
    ) {
        postFacadeGateway.create(currentUserDto.id(), request);
        return ResponseEntity.ok(new BaseResponse<>(ResponseCodeAndMessages.FEED_CREATE_SUCCESS, null));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<BaseResponse<PostDetailResponse>> findDetailPost(
            @PathVariable String postId,
            @CurrentUser CurrentUserDto currentUserDto
    ) {
        PostDetailResponse postDetailResponse = postFacadeGateway.find(currentUserDto.id(), postId);
        return ResponseEntity.ok(new BaseResponse<>(ResponseCodeAndMessages.FEED_FIND_SUCCESS, postDetailResponse));
    }

    @GetMapping
    public ResponseEntity<BaseResponse<PostListResponse>> findAllDetailPostByLocation(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam(defaultValue = "3") int level,
            @PageableDefault(size = 6) Pageable pageable,
            @CurrentUser CurrentUserDto currentUserDto
    ) {
        LocationSquareRangeRequest locationSquareRangeRequest = new LocationSquareRangeRequest(latitude, longitude, level);
        PostListResponse postListResponse = postFacadeGateway.findAll(currentUserDto.id(), locationSquareRangeRequest, pageable);
        return ResponseEntity.ok(new BaseResponse<>(ResponseCodeAndMessages.FEED_FIND_ALL_SUCCESS, postListResponse));
    }

    @PostMapping("/scraps")
    public ResponseEntity<BaseResponse<Void>> scrapPost(
            @Valid @RequestBody PostScrapRequest request,
            @CurrentUser CurrentUserDto currentUserDto
    ) {
        postFacadeGateway.scrap(currentUserDto.id(), request.postId());
        return ResponseEntity.ok(new BaseResponse<>(ResponseCodeAndMessages.FEED_SCRAP_SUCCESS, null));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<BaseResponse<Void>> deletePost(
            @PathVariable String postId,
            @CurrentUser CurrentUserDto currentUserDto
    ) {
        postFacadeGateway.delete(currentUserDto.id(), postId);
        return ResponseEntity.ok(new BaseResponse<>(ResponseCodeAndMessages.FEED_DELETE_SUCCESS, null));
    }

    @PutMapping("/{postId}/like")
    public ResponseEntity<BaseResponse<Void>> likePost(
            @PathVariable String postId,
            @CurrentUser CurrentUserDto currentUserDto
    ) {
        postFacadeGateway.likePost(currentUserDto.id(), postId);
        return ResponseEntity.ok(new BaseResponse<>(ResponseCodeAndMessages.FEED_LIKE_SUCCESS, null));
    }

    @PutMapping("/{postId}/unlike")
    public ResponseEntity<BaseResponse<Void>> unlikePost(
            @PathVariable String postId,
            @CurrentUser CurrentUserDto currentUserDto
    ) {
        postFacadeGateway.unlikePost(currentUserDto.id(), postId);
        return ResponseEntity.ok(new BaseResponse<>(ResponseCodeAndMessages.FEED_UNLIKE_SUCCESS, null));
    }
}
