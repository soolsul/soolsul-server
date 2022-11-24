package com.soolsul.soolsulserver.user.auth.exception;

import com.soolsul.soolsulserver.common.exception.SoolsulBusinessException;

public class UserUnauthorizedException extends SoolsulBusinessException {

    private static final String USER_UNAUTHORIZED_MESSAGE = "해당 유저는 인증되지 않았습니다.";

    public UserUnauthorizedException() {
        super(USER_UNAUTHORIZED_MESSAGE);
    }
}
