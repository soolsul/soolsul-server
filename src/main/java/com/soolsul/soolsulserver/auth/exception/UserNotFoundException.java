package com.soolsul.soolsulserver.auth.exception;

import com.soolsul.soolsulserver.common.exception.SoolsulBusinessException;

public class UserNotFoundException extends SoolsulBusinessException {

    private static final String USER_NOT_FOUND_MESSAGE = "해당 사용자를 찾을 수 없습니다.";

    public UserNotFoundException() {
        super(USER_NOT_FOUND_MESSAGE);
    }
}
