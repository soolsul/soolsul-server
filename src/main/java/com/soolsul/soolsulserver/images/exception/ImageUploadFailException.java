package com.soolsul.soolsulserver.images.exception;

import com.soolsul.soolsulserver.common.exception.SoolsulBusinessException;

public class ImageUploadFailException extends SoolsulBusinessException {

    private static final String IMAGE_UPLOAD_FAIL_MESSAGE = "이미지 업로드에 실패하였습니다";

    public ImageUploadFailException() {
        super(IMAGE_UPLOAD_FAIL_MESSAGE);
    }

}
