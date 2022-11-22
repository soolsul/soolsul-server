package com.soolsul.soolsulserver.user.mypage.facade;

import com.soolsul.soolsulserver.user.auth.repository.dto.UserLookUpResponse;

public interface MyPageQueryFacadeSpec {
    UserLookUpResponse findUserWithDetailInfo(String userId);
}
