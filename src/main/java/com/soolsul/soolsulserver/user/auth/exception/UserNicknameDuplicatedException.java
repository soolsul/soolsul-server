package com.soolsul.soolsulserver.user.auth.exception;

import com.soolsul.soolsulserver.common.exception.SoolsulBusinessException;

public class UserNicknameDuplicatedException extends SoolsulBusinessException {

    private static final String USER_NICKNAME_MESSAGE = "이미 사용중인 별칭 입니다.";

    public UserNicknameDuplicatedException() {
        super(USER_NICKNAME_MESSAGE);
    }
}
