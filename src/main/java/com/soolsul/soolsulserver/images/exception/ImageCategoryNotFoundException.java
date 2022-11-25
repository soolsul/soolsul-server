package com.soolsul.soolsulserver.images.exception;

import com.soolsul.soolsulserver.common.exception.SoolsulBusinessException;

public class ImageCategoryNotFoundException extends SoolsulBusinessException {

    private static final String IMAGE_CATEGORY_NOT_FOUND_MESSAGE = "이미지 카테고리가 존재하지 않습니다.";

    public ImageCategoryNotFoundException() {
        super(IMAGE_CATEGORY_NOT_FOUND_MESSAGE);
    }

}
