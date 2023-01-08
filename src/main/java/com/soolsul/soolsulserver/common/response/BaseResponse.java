package com.soolsul.soolsulserver.common.response;

public record BaseResponse<T>(String code, String message, T data) {

}

