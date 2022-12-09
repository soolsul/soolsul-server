package com.soolsul.soolsulserver.post.facade;

import com.soolsul.soolsulserver.user.auth.business.CustomUserDetailsService;
import com.soolsul.soolsulserver.user.auth.persistence.dto.response.UserLookUpResponse;
import com.soolsul.soolsulserver.location.common.dto.request.LocationSquareRangeRequest;
import com.soolsul.soolsulserver.location.common.dto.response.LocationSquareRangeCondition;
import com.soolsul.soolsulserver.location.business.LocationRangeService;
import com.soolsul.soolsulserver.post.business.PostQueryService;
import com.soolsul.soolsulserver.post.common.dto.request.PostLookupRequest;
import com.soolsul.soolsulserver.post.domain.Post;
import com.soolsul.soolsulserver.post.common.dto.response.PostDetailResponse;
import com.soolsul.soolsulserver.post.common.dto.response.PostListResponse;
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

        PostLookupRequest postLookupRequest = new PostLookupRequest(userId, postId, findPost, findUser);

        return postQueryService.findPostDetail(postLookupRequest);
    }

    public PostListResponse findAllPostByLocation(String userId, LocationSquareRangeRequest rangeRequest, Pageable pageable) {
        LocationSquareRangeCondition squareRangeCondition = locationRangeService.calculateLocationSquareRange(rangeRequest);

        return postQueryService.findAllPostByLocation(userId, squareRangeCondition, pageable);
    }
}
