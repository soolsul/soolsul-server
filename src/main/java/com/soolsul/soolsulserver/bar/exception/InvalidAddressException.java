package com.soolsul.soolsulserver.bar.exception;

import com.soolsul.soolsulserver.common.exception.SoolsulBusinessException;

public class InvalidAddressException extends SoolsulBusinessException {

    private static final String INVALID_ADDRESS_MESSAGE = "찾을 수 없는 주소 입니다.";

    public InvalidAddressException() {
        super(INVALID_ADDRESS_MESSAGE);
    }
}
