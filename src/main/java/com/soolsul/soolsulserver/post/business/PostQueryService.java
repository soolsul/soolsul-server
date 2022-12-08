package com.soolsul.soolsulserver.post.business;


import com.soolsul.soolsulserver.bar.common.dto.request.BarLookupServiceConditionRequest;
import com.soolsul.soolsulserver.bar.common.dto.response.FilteredBarLookupResponse;
import com.soolsul.soolsulserver.bar.exception.BarNotFoundException;
import com.soolsul.soolsulserver.bar.persistence.BarQueryRepository;
import com.soolsul.soolsulserver.bar.common.dto.response.BarLookupResponse;
import com.soolsul.soolsulserver.curation.common.dto.response.CurationPostLookupResponse;
import com.soolsul.soolsulserver.location.common.dto.response.LocationSquareRangeCondition;
import com.soolsul.soolsulserver.post.common.dto.request.PostLookupRequest;
import com.soolsul.soolsulserver.post.common.dto.response.PostDetailLikeResponse;
import com.soolsul.soolsulserver.post.common.dto.response.PostDetailStoreResponse;
import com.soolsul.soolsulserver.post.common.dto.response.PostDetailUserResponse;
import com.soolsul.soolsulserver.post.domain.Post;
import com.soolsul.soolsulserver.post.domain.PostPhoto;
import com.soolsul.soolsulserver.post.domain.PostRepository;
import com.soolsul.soolsulserver.post.domain.PostScrapRepository;
import com.soolsul.soolsulserver.post.common.dto.response.FilteredPostLookupResponse;
import com.soolsul.soolsulserver.post.common.dto.response.ScrapedPostLookUpResponse;
import com.soolsul.soolsulserver.post.common.dto.response.UserPostLookUpResponse;
import com.soolsul.soolsulserver.post.exception.PostNotFoundException;
import com.soolsul.soolsulserver.post.common.dto.response.PostDetailResponse;
import com.soolsul.soolsulserver.post.common.dto.response.PostListResponse;
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
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostQueryService {

    private final PostRepository postRepository;
    private final PostScrapRepository postScrapRepository;
    private final BarQueryRepository barQueryRepository;

    public Post findById(String postId) {
        return postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
    }

    public PostDetailResponse findPostDetail(PostLookupRequest lookupRequest) {
        BarLookupResponse findBar = barQueryRepository
                .findById(lookupRequest.post().getBarId())
                .orElseThrow(BarNotFoundException::new);

        List<String> imageUrlList = convertImageUrlList(lookupRequest.post());
        boolean userClickedLike = isLoginUserClickedLike(lookupRequest.userId(), lookupRequest.post());

        return new PostDetailResponse(lookupRequest.post(), lookupRequest.findUser(), findBar, imageUrlList, userClickedLike);
    }

    public PostListResponse findAllPostByLocation(String loginUserId, LocationSquareRangeCondition squareRangeCondition, Pageable pageable) {
        BarLookupServiceConditionRequest barLookupServiceConditionRequest = new BarLookupServiceConditionRequest(
                squareRangeCondition.southWestLatitude(),
                squareRangeCondition.southWestLongitude(),
                squareRangeCondition.northEastLatitude(),
                squareRangeCondition.northEastLongitude(),
                Collections.emptyList(),
                Collections.emptyList()
        );

        List<FilteredBarLookupResponse> filteredBars = barQueryRepository.findBarFilteredByConditions(barLookupServiceConditionRequest);
        List<String> barIds = extractBarIds(filteredBars);

        Slice<FilteredPostLookupResponse> postListByLocation = postRepository.findPostListByLocation(barIds, pageable);

        return new PostListResponse(buildPostDetailResponse(loginUserId, postListByLocation, filteredBars));
    }

    public List<ScrapedPostLookUpResponse> findAllScrapedPost(String userId) {
        return postScrapRepository.findAllScrapedPost(userId);
    }

    public List<UserPostLookUpResponse> findAllUserPost(String userId) {
        return postRepository.findAllUserPost(userId);
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

    public List<CurationPostLookupResponse> findAllPostByBarId(String barId) {
        return postRepository.findAllPostByBarId(barId);
    }

}

