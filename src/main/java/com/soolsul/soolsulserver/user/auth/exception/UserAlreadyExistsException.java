package com.soolsul.soolsulserver.user.auth.exception;

import com.soolsul.soolsulserver.common.exception.SoolsulBusinessException;

public class UserAlreadyExistsException extends SoolsulBusinessException {

    public UserAlreadyExistsException() {
        super("해당 가입된 회원 입니다.");
    }
}
