package com.soolsul.soolsulserver.reply.exception;

import com.soolsul.soolsulserver.common.exception.SoolsulBusinessException;

public class ReplyNotFoundException extends SoolsulBusinessException {

    private static final String REPLY_NOT_FOUND_MESSAGE = "해당 댓글을 찾을 수 없습니다.";

    public ReplyNotFoundException() {
        super(REPLY_NOT_FOUND_MESSAGE);
    }
}
