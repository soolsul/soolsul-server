package com.soolsul.soolsulserver.bar.exception;

import com.soolsul.soolsulserver.common.exception.SoolsulBusinessException;

public class BarNotFoundException extends SoolsulBusinessException {

    private static final String NOT_FOUND_MESSAGE = "해당 술집을 찾을 수 없습니다.";

    public BarNotFoundException() {
        super(NOT_FOUND_MESSAGE);
    }
}
