package com.soolsul.soolsulserver.post.business;

import com.soolsul.soolsulserver.bar.businees.dto.BarLookupResponse;
import com.soolsul.soolsulserver.bar.persistence.BarQueryRepository;
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
import com.soolsul.soolsulserver.post.presentation.dto.UserLocationRequest;
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
    private final BarQueryRepository barRepository;

    public PostDetailResponse findPostDetail(String loginUserId, String postId) {
        Post findPost = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
        // TODO : userDetailService에서 Post의 주인을 찾아와야함
        // TODO : Bar 정보 찾아와야 함, 아직 Bar 도메인 미구현 단건 Bar

        List<String> urlList = convertImageUrlList(findPost);

        boolean userClickedLike = isLoginUserClickedLike(loginUserId, findPost);

        return new PostDetailResponse(
                "tempUserName",
                findPost.getScore(),
                findPost.getContents(),
                urlList,
                new PostDetailLikeResponse(findPost.likeCount(), userClickedLike),
                new PostDetailUserResponse("tempPostOwnerId", "tempPostOwnerName", "url"),
                new PostDetailStoreResponse()
        );
    }

    public PostListResponse findAllPostByLocation(String loginUserId, UserLocationRequest locationRequest, Pageable pageable) {
        UserLocation userLocation = UserLocation.from(locationRequest);
        UserLocationBasedSquareRange squareRange = new UserLocationBasedSquareRange(userLocation);

        // TODO : squareRange기반으로 bar목록을 찾아와야함, 아직 미구현 상태라 틀만 잡음

        List<BarLookupResponse> findBars = barRepository.findBarMeetingConditions(null);
        List<String> barIds = extractBarIds(findBars);

        Slice<Post> postListByLocation = postRepository.findPostListByLocation(barIds, pageable);

        return new PostListResponse(lookUpPostDetails(loginUserId, postListByLocation));
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

    private List<PostDetailResponse> lookUpPostDetails(String loginUserId, Slice<Post> postListByLocation) {
        return postListByLocation.stream()
                .map(post -> {
                    boolean userClickedLike = isLoginUserClickedLike(loginUserId, post);
                    List<String> urlList = convertImageUrlList(post);

                    return new PostDetailResponse(
                            "tempUserName",
                            post.getScore(),
                            post.getContents(),
                            urlList,
                            new PostDetailLikeResponse(post.likeCount(), userClickedLike),
                            new PostDetailUserResponse("tempPostOwnerId", "tempPostOwnerName", "url"),
                            new PostDetailStoreResponse()
                    );
                })
                .collect(Collectors.toList());
    }

    private List<String> extractBarIds(List<BarLookupResponse> findBars) {
        return findBars.stream()
                .map(bar -> bar.barId())
                .collect(Collectors.toList());
    }
}

