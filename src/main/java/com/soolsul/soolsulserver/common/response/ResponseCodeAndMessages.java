package com.soolsul.soolsulserver.common.response;

public enum ResponseCodeAndMessages {
    /* USER */
    USER_CREATE_SUCCESS("U001", "유저 생성에 성공했습니다."),
    USER_LOGIN_SUCCESS("U002", "로그인에 성공했습니다."),
    USER_DELETE_SUCCESS("U003", "유저 삭제에 성공했습니다."),
    USER_UNAUTHENTICATED("U004", "해당 유저는 인증되지 않았습니다."),
    USER_UNAUTHORIZED("U005", "해당 유저는 권한이 없습니다."),
    USER_LOOK_UP_SUCCESS("U006", "해당 유저 정보를 찾는데 성공했습니다."),
    USER_LOGOUT_SUCCESS("U007", "로그아웃에 성공하였습니다."),
    USER_DUPLICATED("U008", "이미 가입된 회원 입니다."),

    /* MYPAGE */
    MYPAGE_POSTS_FIND_SUCCESS("M001", "유저의 피드 조회에 성공했습니다."),
    MYPAGE_REPLIES_FIND_SUCCESS("M002", "유저의 댓글 조회에 성공했습니다."),
    MYPAGE_USER_INFO_FIND_SUCCESS("M003", "유저의 기본 정보 조회에 성공했습니다."),
    MYPAGE_USER_INFO_EDIT_SUCCESS("M004", "유저의 기본 정보 수정에 성공했습니다."),

    /* FEED */
    FEED_CREATE_SUCCESS("P001", "피드 생성 성공했습니다."),
    FEED_FIND_SUCCESS("P002", "피드 찾기에 성공하였습니다."),
    // POO2 : update
    FEED_DELETE_SUCCESS("P003", "피드 삭제에 성공하였습니다."),
    FEED_FIND_ALL_SUCCESS("P004", "모든 피드를 찾는데 성공하였습니다."),
    FEED_SCRAP_SUCCESS("P005", "피드 스크랩을 성공했습니다."),
    FEED_FIND_ALL_SCRAP_SUCCESS("P006", "모든 스크랩 된 피드를 찾는데 성공하였습니다."),
    FEED_LIKE_SUCCESS("P007", "피드 좋아요에 성공하였습니다."),
    FEED_UNLIKE_SUCCESS("P008", "피드 좋아요 취소에 성공하였습니다."),

    /* BAR */
    BAR_LOOK_UP_SUCCESS("B001", "술집 목록 조회에 성공하였습니다."),

    /* CURATION */
    CURATIONS_LOOK_UP_SUCCESS("C001", "큐레이션 목록 조회에 성공하였습니다."),
    CURATION_DETAILS_LOOK_UP_SUCCESS("C002", "큐레이션 상세 조회를 성공하였습니다"),

    /* REPLY */
    REPLY_CREATE_SUCCESS("R001", "댓글을 추가하는데 성공하였습니다."),
    REPLY_READ_SUCCESS("R002", "댓글을 읽는데 성공하였습니다."),
    REPLY_UPDATE_SUCCESS("R003", "댓글을 수정하는데 성공하였습니다."),
    REPLY_DELETE_SUCCESS("R004", "댓글을 삭제하는데 성공하였습니다.");

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
