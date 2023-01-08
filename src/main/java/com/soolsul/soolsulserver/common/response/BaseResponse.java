package com.soolsul.soolsulserver.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public record BaseResponse<T>(String code, String message, T data) {

}

