package com.soolsul.soolsulserver.user.auth.exception;

import com.soolsul.soolsulserver.common.exception.SoolsulBusinessException;

public class UserInvalidIdException extends SoolsulBusinessException {

    private static final String ID_EXCEPTION_MESSAGE = "정상적인 ID값이 아닙니다.";

    public UserInvalidIdException() {
        super(ID_EXCEPTION_MESSAGE);
    }
}
