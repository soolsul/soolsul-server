package com.soolsul.soolsulserver.reply.exception;

import com.soolsul.soolsulserver.common.exception.SoolsulBusinessException;

public class InvalidReplyContentException extends SoolsulBusinessException {

    private static final String INVALID_REPLY_LENGTH_MESSAGE = "부적합한 댓글 길이 입니다.";

    public InvalidReplyContentException() {
        super(INVALID_REPLY_LENGTH_MESSAGE);
    }
}
