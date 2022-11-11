package com.soolsul.soolsulserver.post.exception;

import com.soolsul.soolsulserver.common.exception.SoolsulBusinessException;

public class PostNotFoundException extends SoolsulBusinessException {

    private static final String POST_NOT_FOUND_MESSAGE = "해당 피드를 찾을 수 없습니다.";

    public PostNotFoundException() {
        super(POST_NOT_FOUND_MESSAGE);
    }
}
