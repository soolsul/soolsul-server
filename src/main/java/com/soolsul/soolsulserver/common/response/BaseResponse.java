package com.soolsul.soolsulserver.common.response;

import lombok.Getter;

@Getter
public class BaseResponse<T> {

    private final String code;
    private final String message;
    private final T data;

    public BaseResponse(ResponseCodeAndMessages response, T data) {
        this(response.getCode(), response.getMessage(), data);
    }

    public BaseResponse(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}

