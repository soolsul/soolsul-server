package com.soolsul.soolsulserver.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.soolsul.soolsulserver.acceptance.AuthStep.로그인_되어_있음;
import static com.soolsul.soolsulserver.acceptance.PostStep.피드_목록_조회_요청;
import static com.soolsul.soolsulserver.acceptance.PostStep.피드_생성_요청;
import static com.soolsul.soolsulserver.acceptance.PostStep.피드_생성_정보_생성;
import static com.soolsul.soolsulserver.acceptance.PostStep.피드_조회_응답_확인;
import static com.soolsul.soolsulserver.acceptance.ReplyStep.댓글_추가_요청_응답_확인;
import static com.soolsul.soolsulserver.acceptance.ReplyStep.피드에_댓글_추가_요청;

public class ReplyAcceptanceTest extends AcceptanceTest {

    /**
     * given: 피드가 하나 생성되어 있다.
     * and: 사용자가 피드 단건을 보고 있다.
     * when: 특정 피드에 댓글을 추가한다.
     * then: 정상적으로 댓글이 추가된다.
     */
    @DisplayName("Post에 댓글을 추가할 수 있다.")
    @Test
    public void add_reply_test() {
        // given
        String accessToken = 로그인_되어_있음(USER_EMAIL, USER_PASSWORD);
        피드_생성_요청(accessToken, 피드_생성_정보_생성());
        var 피드_목록_조회_응답 = 피드_목록_조회_요청(accessToken);
        String 첫_피드_아이디 = 피드_조회_응답_확인(피드_목록_조회_응답);

        // when
        ExtractableResponse<Response> 댓글_추가_요청_응답 = 피드에_댓글_추가_요청(accessToken, 첫_피드_아이디);

        // then
        댓글_추가_요청_응답_확인(댓글_추가_요청_응답);
    }
}
