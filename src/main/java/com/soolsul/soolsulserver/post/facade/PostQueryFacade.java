package com.soolsul.soolsulserver.post.facade;

import com.soolsul.soolsulserver.auth.business.CustomUserDetailsService;
import com.soolsul.soolsulserver.auth.repository.dto.UserLookUpResponse;
import com.soolsul.soolsulserver.location.request.LocationSquareRangeRequest;
import com.soolsul.soolsulserver.location.response.LocationSquareRangeCondition;
import com.soolsul.soolsulserver.location.service.LocationRangeService;
import com.soolsul.soolsulserver.post.business.PostQueryService;
import com.soolsul.soolsulserver.post.domain.Post;
import com.soolsul.soolsulserver.post.presentation.dto.PostDetailResponse;
import com.soolsul.soolsulserver.post.presentation.dto.PostListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostQueryFacade {

    private final PostQueryService postQueryService;
    private final CustomUserDetailsService userDetailsService;
    private final LocationRangeService locationRangeService;

    public PostDetailResponse findPostDetail(String userId, String postId) {
        Post findPost = postQueryService.findById(postId);
        UserLookUpResponse findUser = userDetailsService.findUserWithDetailInfo(findPost.getOwnerId());

        return postQueryService.findPostDetail(userId, postId, findPost, findUser);
    }

    public PostListResponse findAllPostByLocation(String userId, LocationSquareRangeRequest rangeRequest, Pageable pageable) {
        LocationSquareRangeCondition squareRangeCondition = locationRangeService.calculateLocationSquareRange(rangeRequest);

        return postQueryService.findAllPostByLocation(userId, squareRangeCondition, pageable);
    }
}
