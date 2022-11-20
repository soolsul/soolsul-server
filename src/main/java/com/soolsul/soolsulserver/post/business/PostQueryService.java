package com.soolsul.soolsulserver.post.business;


import com.soolsul.soolsulserver.auth.business.CustomUserDetailsService;
import com.soolsul.soolsulserver.auth.repository.dto.UserLookUpResponse;
import com.soolsul.soolsulserver.bar.businees.dto.BarLookupServiceConditionRequest;
import com.soolsul.soolsulserver.bar.businees.dto.FilteredBarLookupResponse;
import com.soolsul.soolsulserver.bar.exception.BarNotFoundException;
import com.soolsul.soolsulserver.bar.persistence.BarQueryRepository;
import com.soolsul.soolsulserver.bar.presentation.dto.BarLookupResponse;
import com.soolsul.soolsulserver.location.request.LocationSquareRangeRequest;
import com.soolsul.soolsulserver.location.response.LocationSquareRangeCondition;
import com.soolsul.soolsulserver.location.service.LocationRangeService;
import com.soolsul.soolsulserver.post.business.dto.PostDetailLikeResponse;
import com.soolsul.soolsulserver.post.business.dto.PostDetailStoreResponse;
import com.soolsul.soolsulserver.post.business.dto.PostDetailUserResponse;
import com.soolsul.soolsulserver.post.domain.Post;
import com.soolsul.soolsulserver.post.domain.PostPhoto;
import com.soolsul.soolsulserver.post.domain.PostRepository;
import com.soolsul.soolsulserver.post.domain.dto.FilteredPostLookupResponse;
import com.soolsul.soolsulserver.post.exception.PostNotFoundException;
import com.soolsul.soolsulserver.post.presentation.dto.PostDetailResponse;
import com.soolsul.soolsulserver.post.presentation.dto.PostListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostQueryService {

    private final PostRepository postRepository;
    private final BarQueryRepository barQueryRepository;
    private final CustomUserDetailsService userDetailsService;
    private final LocationRangeService locationRangeService;

    public PostDetailResponse findPostDetail(String loginUserId, String postId) {
        Post findPost = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        UserLookUpResponse findUser = userDetailsService.findUserWithDetailInfo(findPost.getOwnerId());
        BarLookupResponse findBar = barQueryRepository.findById(findPost.getBarId())
                .orElseThrow(BarNotFoundException::new);


        List<String> imageUrlList = convertImageUrlList(findPost);
        boolean userClickedLike = isLoginUserClickedLike(loginUserId, findPost);

        return new PostDetailResponse(findPost, findUser, findBar, imageUrlList, userClickedLike);
    }

    public PostListResponse findAllPostByLocation(String loginUserId, LocationSquareRangeRequest locationSquareRangeRequest, Pageable pageable) {
        LocationSquareRangeCondition locationSquareRangeCondition = locationRangeService.calculateLocationSquareRange(
                locationSquareRangeRequest
        );

        BarLookupServiceConditionRequest barLookupServiceConditionRequest = new BarLookupServiceConditionRequest(
                locationSquareRangeCondition.southWestLatitude(),
                locationSquareRangeCondition.southWestLongitude(),
                locationSquareRangeCondition.northEastLatitude(),
                locationSquareRangeCondition.northEastLongitude(),
                Collections.emptyList(),
                Collections.emptyList()
        );

        List<FilteredBarLookupResponse> filteredBars = barQueryRepository.findBarFilteredByConditions(barLookupServiceConditionRequest);
        List<String> barIds = extractBarIds(filteredBars);

        Slice<FilteredPostLookupResponse> postListByLocation = postRepository.findPostListByLocation(barIds, pageable);

        return new PostListResponse(buildPostDetailResponse(loginUserId, postListByLocation, filteredBars));
    }

    private boolean isLoginUserClickedLike(String loginUserId, Post findPost) {
        return !Objects.isNull(loginUserId) && findPost.isLikeContain(loginUserId);
    }

    private List<String> convertImageUrlList(Post findPost) {
        return findPost.getPhotos()
                .stream()
                .map(PostPhoto::getUrl)
                .collect(Collectors.toList());
    }

    private List<String> extractBarIds(List<FilteredBarLookupResponse> findBars) {
        return findBars.stream()
                .map(FilteredBarLookupResponse::barId)
                .collect(Collectors.toList());
    }

    private List<PostDetailResponse> buildPostDetailResponse(
            String loginUserId,
            Slice<FilteredPostLookupResponse> postListByLocation,
            List<FilteredBarLookupResponse> filteredBarList
    ) {
        return postListByLocation.stream()
                .map(postDto -> {
                    boolean userClickedLike = isLoginUserClickedLike(loginUserId, postDto.post());

                    FilteredBarLookupResponse matchedBar = filteredBarList.stream()
                            .filter(f -> Objects.equals(f.barId(), postDto.post().getBarId()))
                            .findFirst()
                            .orElseThrow(BarNotFoundException::new);

                    return new PostDetailResponse(
                            postDto.post().getId(),
                            postDto.post().getScore(),
                            postDto.post().getContents(),
                            convertImageUrlList(postDto.post()),
                            new PostDetailLikeResponse(postDto.post().likeCount(), userClickedLike),
                            new PostDetailUserResponse(postDto.userInfo().getUserId(), postDto.userInfo().getNickname(), postDto.userInfo().getProfileImage()),
                            new PostDetailStoreResponse(matchedBar.barId(), matchedBar.barName(), matchedBar.barDescription())
                    );
                })
                .collect(Collectors.toList());
    }
}

