package com.soolsul.soolsulserver.user.mypage.facade;

import com.soolsul.soolsulserver.user.auth.business.CustomUserDetailsService;
import com.soolsul.soolsulserver.user.mypage.presentation.dto.reqeust.UserInfoEditRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class MyPageCommandFacade implements MyPageCommandFacadeSpec {

    private final CustomUserDetailsService userDetailsService;

    @Override
    public void editUserInfo(UserInfoEditRequest editRequest, String userId) {
        userDetailsService.editUserInformation(editRequest, userId);
    }
}
