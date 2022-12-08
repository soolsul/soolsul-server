package com.soolsul.soolsulserver.user.mypage.facade;

import com.soolsul.soolsulserver.user.mypage.common.dto.reqeust.UserInfoEditRequest;

public interface MyPageCommandFacadeSpec {

    void editUserInfo(UserInfoEditRequest editRequest, String userId);
}
