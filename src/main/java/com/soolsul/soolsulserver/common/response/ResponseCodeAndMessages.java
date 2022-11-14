package com.soolsul.soolsulserver.common.response;

public enum ResponseCodeAndMessages {
    FEED_CREATE_SUCCESS("P001", "피드 생성 성공했습니다."),
    FEED_FIND_SUCCESS("P002", "피드 찾기에 성공하였습니다."),
    // POO2 : update
    // POO3 : delete
    FEED_FIND_ALL_SUCCESS("P004", "모든 피드를 찾는데 성공하였습니다."),

    BAR_LOOK_UP_SUCCESS("B001", "술집 목록 조회에 성공하였습니다.");


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
