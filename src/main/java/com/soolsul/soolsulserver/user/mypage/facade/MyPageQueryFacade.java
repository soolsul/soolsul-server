package com.soolsul.soolsulserver.user.mypage.facade;

import com.soolsul.soolsulserver.user.auth.business.CustomUserDetailsService;
import com.soolsul.soolsulserver.user.auth.repository.dto.UserLookUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyPageQueryFacade implements MyPageQueryFacadeSpec {

    private final CustomUserDetailsService userDetailsService;

    @Override
    public UserLookUpResponse findUserWithDetailInfo(String userId) {
        return userDetailsService.findUserWithDetailInfo(userId);
    }
}
