package com.soolsul.soolsulserver.acceptance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.soolsul.soolsulserver.acceptance.AuthStep.권한_없는_요청;
import static com.soolsul.soolsulserver.acceptance.AuthStep.로그아웃_요청;
import static com.soolsul.soolsulserver.acceptance.AuthStep.로그아웃_응답_확인;
import static com.soolsul.soolsulserver.acceptance.AuthStep.로그인_되어_있음;
import static com.soolsul.soolsulserver.acceptance.AuthStep.베어러_인증으로_내_회원_정보_조회_요청;
import static com.soolsul.soolsulserver.acceptance.AuthStep.회원_정보_조회;
import static com.soolsul.soolsulserver.acceptance.PostStep.피드_목록_조회_요청;

public class AuthAcceptanceTest extends AcceptanceTest {

    /**
     * Given 이미 가입된 사용자가 있고
     * And 아직 로그인하지 않은 상태일때
     * When Bearer Token 인증으로 로그인을 시도하면
     * Then 성공적으로 로그인된다
     */
    @DisplayName("Bearer Token 인증방식의 로그인")
    @Test
    public void bearer_token_login() {
        // given
        String accessToken = 로그인_되어_있음(USER_EMAIL, USER_PASSWORD);

        // when
        var 베어러_로그인_응답 = 베어러_인증으로_내_회원_정보_조회_요청(accessToken);

        // then
        회원_정보_조회(베어러_로그인_응답, USER_EMAIL, NAME);
    }


    /**
     * Given 마이페이지를 보고있는 User가
     * When “로그아웃”을 버튼을 누르면
     * Then 로그아웃되어 Guest상태가 된다.
     * When 제거된 토큰으로 피드 목록 조회 요청시
     * Then 인증 에러가 발생한다.
     */
    @DisplayName("Bearer Token 인증방식의 로그아웃")
    @Test
    public void bearer_token_logout() {
        // given
        String accessToken = 로그인_되어_있음(USER_EMAIL, USER_PASSWORD);

        // when
        var 로그아웃_응답 = 로그아웃_요청(accessToken);

        // then
        로그아웃_응답_확인(로그아웃_응답);

        // when
        var 피드_목록_조회_응답 = 피드_목록_조회_요청(accessToken);

        // then
        권한_없는_요청(피드_목록_조회_응답);
    }
}
