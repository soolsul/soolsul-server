package com.soolsul.soolsulserver.images.exception;

import com.soolsul.soolsulserver.common.exception.SoolsulBusinessException;

public class InvalidImageExtensionException extends SoolsulBusinessException {

    private static final String INVALID_IMAGE_EXTENSION_MESSAGE = "올바른 이미지 확장자가 아닙니다";

    public InvalidImageExtensionException() {
        super(INVALID_IMAGE_EXTENSION_MESSAGE);
    }

}
