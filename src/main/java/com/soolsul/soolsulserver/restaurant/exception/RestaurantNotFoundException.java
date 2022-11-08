package com.soolsul.soolsulserver.restaurant.exception;

import com.soolsul.soolsulserver.common.exception.SoolsulBusinessException;

public class RestaurantNotFoundException extends SoolsulBusinessException {

    private static final String NOT_FOUND_MESSAGE = "해당 가게를 찾을 수 없습니다.";

    public RestaurantNotFoundException() {
        super(NOT_FOUND_MESSAGE);
    }
}
