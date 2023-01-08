package com.soolsul.soolsulserver.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseCodes {
    /* USER */
    USER_CREATE_SUCCESS("U001"),
    USER_LOGIN_SUCCESS("U002"),
    USER_DELETE_SUCCESS("U003"),
    USER_UNAUTHENTICATED("U004"),
    USER_UNAUTHORIZED("U005"),
    USER_LOOK_UP_SUCCESS("U006"),
    USER_LOGOUT_SUCCESS("U007"),
    USER_DUPLICATED("U008"),

    /* MYPAGE */
    MYPAGE_POSTS_FIND_SUCCESS("M001"),
    MYPAGE_REPLIES_FIND_SUCCESS("M002"),
    MYPAGE_USER_INFO_FIND_SUCCESS("M003"),
    MYPAGE_USER_INFO_EDIT_SUCCESS("M004"),

    /* FEED */
    FEED_CREATE_SUCCESS("P001"),
    FEED_FIND_SUCCESS("P002"),
    // POO2 : update
    // POO3 : delete
    FEED_FIND_ALL_SUCCESS("P004"),
    FEED_SCRAP_SUCCESS("P005"),
    FEED_FIND_ALL_SCRAP_SUCCESS("P006"),

    /* BAR */
    BAR_LOOK_UP_SUCCESS("B001"),

    /* CURATION */
    CURATIONS_LOOK_UP_SUCCESS("C001"),
    CURATION_DETAILS_LOOK_UP_SUCCESS("C002"),

    /* REPLY */
    REPLY_CREATE_SUCCESS("R001"),
    REPLY_READ_SUCCESS("R002");

    private final String code;

}
