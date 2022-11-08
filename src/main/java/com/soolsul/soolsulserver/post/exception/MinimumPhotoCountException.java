package com.soolsul.soolsulserver.post.exception;

import com.soolsul.soolsulserver.common.exception.SoolsulBusinessException;

public class MinimumPhotoCountException extends SoolsulBusinessException {

    private static final String MINIMUM_PHOTO_COUNT_MESSAGE = "사진은 최소 한장 이상이 있어야 합니다.";

    public MinimumPhotoCountException() {
        super(MINIMUM_PHOTO_COUNT_MESSAGE);
    }
}
