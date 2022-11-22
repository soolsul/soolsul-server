package com.soolsul.soolsulserver.mypage.presentation;

import com.soolsul.soolsulserver.auth.CustomUser;
import com.soolsul.soolsulserver.auth.business.CustomUserDetailsService;
import com.soolsul.soolsulserver.auth.repository.dto.UserLookUpResponse;
import com.soolsul.soolsulserver.common.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.soolsul.soolsulserver.common.response.ResponseCodeAndMessages.USER_LOOK_UP_SUCCESS;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/mypages")
public class MyPageController {

    private final CustomUserDetailsService userDetailsService;

    @GetMapping("/me")
    public ResponseEntity<BaseResponse<UserLookUpResponse>> searchUser(Authentication authentication) {
        CustomUser user = (CustomUser) authentication.getPrincipal();
        UserLookUpResponse userLookUpResponse = userDetailsService.findUserWithDetailInfo(user.getId());
        return ResponseEntity.ok(new BaseResponse<>(USER_LOOK_UP_SUCCESS, userLookUpResponse));
    }
}
