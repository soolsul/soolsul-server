package com.soolsul.soolsulserver.user.mypage.facade;

import com.soolsul.soolsulserver.user.mypage.presentation.dto.response.ScrapedPostListLookUpResponse;
import com.soolsul.soolsulserver.user.auth.repository.dto.UserLookUpResponse;
import com.soolsul.soolsulserver.user.mypage.presentation.dto.response.UserPostListLookUpResponse;

public interface MyPageQueryFacadeSpec {
    UserLookUpResponse findUserWithDetailInfo(String userId);

    ScrapedPostListLookUpResponse findAllScrapedPost(String userId);

    UserPostListLookUpResponse findAllUserPost(String userId);
}
