package com.soolsul.soolsulserver.post.exception;

import com.soolsul.soolsulserver.common.exception.SoolsulBusinessException;

public class InvalidPostOwnerException extends SoolsulBusinessException {

    private static final String POST_OWNER_EXCEPTION_MESSAGE = "해당 피드의 작성자가 아닙니다.";

    public InvalidPostOwnerException() {
        super(POST_OWNER_EXCEPTION_MESSAGE);
    }
}
