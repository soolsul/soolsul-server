package com.soolsul.soolsulserver.user.mypage.facade;

import com.soolsul.soolsulserver.post.business.PostQueryService;
import com.soolsul.soolsulserver.post.domain.dto.ScrapedPostLookUpResponse;
import com.soolsul.soolsulserver.post.domain.dto.UserPostLookUpResponse;
import com.soolsul.soolsulserver.user.auth.business.CustomUserDetailsService;
import com.soolsul.soolsulserver.user.auth.repository.dto.UserLookUpResponse;
import com.soolsul.soolsulserver.user.mypage.presentation.dto.response.ScrapedPostListLookUpResponse;
import com.soolsul.soolsulserver.user.mypage.presentation.dto.response.UserPostListLookUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyPageQueryFacade implements MyPageQueryFacadeSpec {

    private final CustomUserDetailsService userDetailsService;
    private final PostQueryService postQueryService;

    @Override
    public UserLookUpResponse findUserWithDetailInfo(String userId) {
        return userDetailsService.findUserWithDetailInfo(userId);
    }

    @Override
    public ScrapedPostListLookUpResponse findAllScrapedPost(String userId) {
        List<ScrapedPostLookUpResponse> scrapedPostList = postQueryService.findAllScrapedPost(userId);
        return new ScrapedPostListLookUpResponse(scrapedPostList);
    }

    @Override
    public UserPostListLookUpResponse findAllUserPost(String userId) {
        List<UserPostLookUpResponse> userPostList = postQueryService.findAllUserPost(userId);
        return new UserPostListLookUpResponse(userPostList);
    }
}
