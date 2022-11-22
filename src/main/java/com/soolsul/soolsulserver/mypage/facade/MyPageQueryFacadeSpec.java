package com.soolsul.soolsulserver.mypage.facade;

import com.soolsul.soolsulserver.auth.repository.dto.UserLookUpResponse;

public interface MyPageQueryFacadeSpec {
    UserLookUpResponse findUserWithDetailInfo(String userId);
}
