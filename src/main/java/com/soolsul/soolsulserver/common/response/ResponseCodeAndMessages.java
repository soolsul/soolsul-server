package com.soolsul.soolsulserver.common.response;

public enum ResponseCodeAndMessages {
    // Review
    FEED_CREATE_SUCCESS("P001", "피드 생성 성공했습니다.");

    private final String code;
    private final String message;

    ResponseCodeAndMessages(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
