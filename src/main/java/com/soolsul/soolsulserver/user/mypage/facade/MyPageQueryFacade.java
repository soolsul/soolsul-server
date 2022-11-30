package com.soolsul.soolsulserver.user.mypage.facade;

import com.soolsul.soolsulserver.post.business.PostQueryService;
import com.soolsul.soolsulserver.post.domain.dto.ScrapedPostLookUpResponse;
import com.soolsul.soolsulserver.post.domain.dto.UserPostLookUpResponse;
import com.soolsul.soolsulserver.post.domain.dto.UserReplyLookUpResponse;
import com.soolsul.soolsulserver.reply.business.ReplyQueryService;
import com.soolsul.soolsulserver.user.auth.business.CustomUserDetailsService;
import com.soolsul.soolsulserver.user.auth.repository.dto.response.UserEditFormResponse;
import com.soolsul.soolsulserver.user.auth.repository.dto.response.UserLookUpResponse;
import com.soolsul.soolsulserver.user.mypage.presentation.dto.response.ScrapedPostListLookUpResponse;
import com.soolsul.soolsulserver.user.mypage.presentation.dto.response.UserPostListLookUpResponse;
import com.soolsul.soolsulserver.user.mypage.presentation.dto.response.UserReplyListLookUpResponse;
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
    private final ReplyQueryService replyQueryService;

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

    @Override
    public UserReplyListLookUpResponse findAllUserReplies(String userId) {
        List<UserReplyLookUpResponse> userReplyList = replyQueryService.findAllUserReply(userId);
        return new UserReplyListLookUpResponse(userReplyList);
    }

    @Override
    public UserEditFormResponse findUserEditForm(String userId) {
        UserLookUpResponse userInfo = userDetailsService.findUserWithDetailInfo(userId);
        return new UserEditFormResponse(userInfo.profileImage(), userInfo.nickName(), userInfo.email());
    }
}
