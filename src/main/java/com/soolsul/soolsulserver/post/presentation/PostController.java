package com.soolsul.soolsulserver.post.presentation;

import com.soolsul.soolsulserver.common.response.BaseResponse;
import com.soolsul.soolsulserver.location.common.dto.request.LocationSquareRangeRequest;
import com.soolsul.soolsulserver.post.common.dto.request.PostCreateRequest;
import com.soolsul.soolsulserver.post.common.dto.request.PostScrapRequest;
import com.soolsul.soolsulserver.post.common.dto.response.PostDetailResponse;
import com.soolsul.soolsulserver.post.common.dto.response.PostListResponse;
import com.soolsul.soolsulserver.post.facade.PostFacadeGateway;
import com.soolsul.soolsulserver.user.auth.annotation.CurrentUser;
import com.soolsul.soolsulserver.user.auth.domain.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
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
import java.util.Locale;

import static com.soolsul.soolsulserver.common.response.ResponseCodes.FEED_CREATE_SUCCESS;
import static com.soolsul.soolsulserver.common.response.ResponseCodes.FEED_FIND_ALL_SUCCESS;
import static com.soolsul.soolsulserver.common.response.ResponseCodes.FEED_FIND_SUCCESS;
import static com.soolsul.soolsulserver.common.response.ResponseCodes.FEED_SCRAP_SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostFacadeGateway postFacadeGateway;
    private final MessageSource messageSource;

    @PostMapping
    public ResponseEntity<BaseResponse<Void>> createPost(
            @Valid @RequestBody PostCreateRequest request,
            @CurrentUser CustomUser currentUser,
            Locale locale
    ) {
        postFacadeGateway.create(currentUser.getId(), request);

        String message = messageSource.getMessage(FEED_CREATE_SUCCESS.getCode(), new String[]{}, locale);

        BaseResponse<Void> baseResponse = new BaseResponse<>(
                FEED_CREATE_SUCCESS.getCode(),
                message,
                null
        );

        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<BaseResponse<PostDetailResponse>> findDetailPost(
            @PathVariable String postId,
            @CurrentUser CustomUser currentUser,
            Locale locale
    ) {
        PostDetailResponse postDetailResponse = postFacadeGateway.find(currentUser.getId(), postId);

        String message = messageSource.getMessage(FEED_FIND_SUCCESS.getCode(), new String[]{}, locale);

        BaseResponse<PostDetailResponse> baseResponse = new BaseResponse<>(
                FEED_FIND_SUCCESS.getCode(),
                message,
                postDetailResponse
        );

        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping
    public ResponseEntity<BaseResponse<PostListResponse>> findAllDetailPostByLocation(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam(defaultValue = "3") int level,
            @PageableDefault(size = 6) Pageable pageable,
            @CurrentUser CustomUser currentUser,
            Locale locale
    ) {
        LocationSquareRangeRequest locationSquareRangeRequest = new LocationSquareRangeRequest(latitude, longitude, level);
        PostListResponse postListResponse = postFacadeGateway.findAll(currentUser.getId(), locationSquareRangeRequest, pageable);

        String message = messageSource.getMessage(FEED_FIND_ALL_SUCCESS.getCode(), new String[]{}, locale);

        BaseResponse<PostListResponse> baseResponse = new BaseResponse<>(
                FEED_FIND_ALL_SUCCESS.getCode(),
                message,
                postListResponse
        );
        return ResponseEntity.ok(baseResponse);
    }

    @PostMapping("/scraps")
    public ResponseEntity<BaseResponse<Void>> scrapPost(
            @Valid @RequestBody PostScrapRequest request,
            @CurrentUser CustomUser currentUser,
            Locale locale
    ) {
        postFacadeGateway.scrap(currentUser.getId(), request.postId());

        String message = messageSource.getMessage(FEED_SCRAP_SUCCESS.getCode(), new String[]{}, locale);

        BaseResponse<Void> baseResponse = new BaseResponse<>(
                FEED_SCRAP_SUCCESS.getCode(),
                message,
                null
        );

        return ResponseEntity.ok(baseResponse);
    }

}
