package com.soolsul.soolsulserver.bar.exception;

import com.soolsul.soolsulserver.common.exception.SoolsulBusinessException;

public class BarNotFoundException extends SoolsulBusinessException {

    private static final String BAR_NOT_FOUND_MESSAGE = "해당 가게를 찾을 수 없습니다.";

    public BarNotFoundException() {
        super(BAR_NOT_FOUND_MESSAGE);
    }
}
