package com.soolsul.soolsulserver.post.business;


import com.soolsul.soolsulserver.auth.business.CustomUserDetailsService;
import com.soolsul.soolsulserver.auth.repository.dto.UserLookUpResponse;
import com.soolsul.soolsulserver.bar.businees.dto.BarLookupServiceConditionRequest;
import com.soolsul.soolsulserver.bar.businees.dto.FilteredBarLookupResponse;
import com.soolsul.soolsulserver.bar.exception.BarNotFoundException;
import com.soolsul.soolsulserver.bar.persistence.BarQueryRepository;
import com.soolsul.soolsulserver.bar.presentation.dto.BarLookupResponse;
import com.soolsul.soolsulserver.common.userlocation.UserLocation;
import com.soolsul.soolsulserver.common.userlocation.UserLocationBasedSquareRange;
import com.soolsul.soolsulserver.post.business.dto.PostDetailLikeResponse;
import com.soolsul.soolsulserver.post.business.dto.PostDetailStoreResponse;
import com.soolsul.soolsulserver.post.business.dto.PostDetailUserResponse;
import com.soolsul.soolsulserver.post.domain.Post;
import com.soolsul.soolsulserver.post.domain.PostPhoto;
import com.soolsul.soolsulserver.post.domain.PostRepository;
import com.soolsul.soolsulserver.post.exception.PostNotFoundException;
import com.soolsul.soolsulserver.post.presentation.dto.PostDetailResponse;
import com.soolsul.soolsulserver.post.presentation.dto.PostListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public PostDetailResponse findPostDetail(String loginUserId, String postId) {
        Post findPost = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        UserLookUpResponse findUser = userDetailsService.findUserWithDetailInfo(findPost.getOwnerId());
        BarLookupResponse findBar = barQueryRepository.findById(findPost.getBarId())
                .orElseThrow(BarNotFoundException::new);

        List<String> urlList = convertImageUrlList(findPost);

        boolean userClickedLike = isLoginUserClickedLike(loginUserId, findPost);

        return new PostDetailResponse(
                findUser.nickName(),
                findPost.getScore(),
                findPost.getContents(),
                urlList,
                new PostDetailLikeResponse(findPost.likeCount(), userClickedLike),
                new PostDetailUserResponse(findUser.userId(), findUser.nickName(), findUser.profileImage()),
                new PostDetailStoreResponse(findBar.id(), findBar.name(), findBar.description())
        );
    }

    public PostListResponse findAllPostByLocation(String loginUserId, UserLocation userLocation, Pageable pageable) {
        UserLocationBasedSquareRange squareRange = new UserLocationBasedSquareRange(userLocation);

        // TODO : 위경도 순서가 뭔가 이상함, 우선 작동하게 배치함
        BarLookupServiceConditionRequest lookupCondition = new BarLookupServiceConditionRequest(
                squareRange.getMinX(), squareRange.getMaxY(), squareRange.getMaxX(), squareRange.getMinY(),
                null, null);

        List<FilteredBarLookupResponse> filteredBars = barQueryRepository.findBarFilteredByConditions(lookupCondition);
        List<String> barIds = extractBarIds(filteredBars);

        Slice<Post> postListByLocation = postRepository.findPostListByLocation(barIds, pageable);

        return new PostListResponse(lookUpPostDetails(loginUserId, postListByLocation, filteredBars));
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
                .map(bar -> bar.barId())
                .collect(Collectors.toList());
    }

    private List<PostDetailResponse> lookUpPostDetails(String loginUserId, Slice<Post> postListByLocation, List<FilteredBarLookupResponse> filteredBarList) {
        return postListByLocation.stream()
                .map(post -> {
                    boolean userClickedLike = isLoginUserClickedLike(loginUserId, post);
                    List<String> urlList = convertImageUrlList(post);

                    FilteredBarLookupResponse matchedBar = filteredBarList.stream()
                            .filter(f -> Objects.equals(f.barId(), post.getBarId()))
                            .findFirst()
                            .orElseThrow(BarNotFoundException::new);

                    return new PostDetailResponse(
                            post.getId(),
                            post.getScore(),
                            post.getContents(),
                            urlList,
                            new PostDetailLikeResponse(post.likeCount(), userClickedLike),
                            new PostDetailUserResponse("tempPostOwnerId", "tempPostOwnerName", "url"),
                            new PostDetailStoreResponse(matchedBar.barId(), matchedBar.barName(), matchedBar.barDescription())
                    );
                })
                .collect(Collectors.toList());
    }
}

