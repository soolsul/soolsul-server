package com.soolsul.soolsulserver.acceptance;

import com.soolsul.soolsulserver.user.auth.presentation.dto.UserRegisterRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static com.soolsul.soolsulserver.acceptance.AuthStep.권한_없는_요청;
import static com.soolsul.soolsulserver.acceptance.AuthStep.로그아웃_요청;
import static com.soolsul.soolsulserver.acceptance.AuthStep.로그아웃_응답_확인;
import static com.soolsul.soolsulserver.acceptance.AuthStep.로그인_되어_있음;
import static com.soolsul.soolsulserver.acceptance.AuthStep.로그인_요청;
import static com.soolsul.soolsulserver.acceptance.AuthStep.베어러_인증으로_내_회원_정보_조회_요청;
import static com.soolsul.soolsulserver.acceptance.AuthStep.유저_로그인_응답_확인;
import static com.soolsul.soolsulserver.acceptance.AuthStep.유저_생성_요청;
import static com.soolsul.soolsulserver.acceptance.AuthStep.유저_생성_응답_확인;
import static com.soolsul.soolsulserver.acceptance.AuthStep.회원_정보_조회;
import static com.soolsul.soolsulserver.acceptance.AuthStep.회원_탈퇴_요청;
import static com.soolsul.soolsulserver.acceptance.AuthStep.회원_탈퇴_응답_확인;
import static com.soolsul.soolsulserver.acceptance.PostStep.피드_목록_조회_요청;

public class AuthAcceptanceTest extends AcceptanceTest {

    /**
     * Given 해당 서비스에 회원가입이 되어있지 않은 이메일이 있다.
     * When 이메일을 통해 회원가입을 한다.
     * Then 사용자의 회원 가입이 완료된다.
     */
    @DisplayName("사용자 회원 가입 테스트")
    @Test
    public void user_register_test() {
        // given
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest("test@email.com",
                "password", "010-1234-5678", "test_user", "test_nickname");

        // when
        var 유저_생성_응답 = 유저_생성_요청(userRegisterRequest);

        // then
        유저_생성_응답_확인(유저_생성_응답, HttpStatus.CREATED, "U001", "유저 생성에 성공했습니다.");
    }

    /**
     * Given 이미 해당 이메일로 가입된 회원이다.
     * When 이메일을 통해 회원가입을 한다.
     * Then 회원가입 실패.
     */
    @DisplayName("이미 가입된 Email로는 다시 가입되지 않는다.")
    @Test
    public void user_register_duplicate_email_exception_test() {
        // given
        로그인_되어_있음(USER_EMAIL, USER_PASSWORD);

        var 유저_생성_정보 = new UserRegisterRequest(USER_EMAIL,
                "password", "010-1234-5678", "test_user", "test_nickname");

        // when
        var 유저_생성_응답 = 유저_생성_요청(유저_생성_정보);

        // then
        유저_생성_응답_확인(유저_생성_응답, HttpStatus.CONFLICT, "U008", "이미 가입된 회원 입니다.");
    }

    /**
     * Given 이미 존재하는 별칭으로 가입된 회원이다.
     * When 이메일을 통해 회원가입을 한다.
     * Then 회원가입 실패.
     */
    @DisplayName("이미 가입된 NickName으로는 다시 가입되지 않는다.")
    @Test
    public void user_register_duplicate_nickname_exception_test() {
        // given
        로그인_되어_있음(USER_EMAIL, USER_PASSWORD);

        var 유저_생성_정보 = new UserRegisterRequest("test@test.com",
                "password", "010-1234-5678", "test_user", NICK_NAME);

        // when
        var 유저_생성_응답 = 유저_생성_요청(유저_생성_정보);

        // then
        유저_생성_응답_확인(유저_생성_응답, HttpStatus.CONFLICT, "U008", "이미 사용중인 별칭 입니다.");
    }

    /**
     * Given 해당 이메일로 가입된 계정이 없다.
     * When 이메일을 통해 로그인 한다.
     * Then 로그인 실패
     */
    @DisplayName("가입되지 않은 이메일을 통해 로그인을 시도하는 경우 실패한다")
    @Test
    public void invalid_email_login_test() {
        // when
        var 로그인_요청_응답 = 로그인_요청("invalid@email.com", USER_PASSWORD);

        // then
        유저_로그인_응답_확인(로그인_요청_응답, HttpStatus.UNAUTHORIZED, "U004", "Invalid Username or Password");
    }

    /**
     * Given 해당 이메일로 생성된 계정이 존재한다.
     * When 잘못된 비밀번호를 통해 로그인 한다.
     * Then 로그인 실패
     */
    @DisplayName("잘못된 비밀번호로 로그인을 시도한다")
    @Test
    public void invalid_password_login_test() {
        // when
        var 로그인_요청_응답 = 로그인_요청(USER_EMAIL, "invalidPassword");

        // then
        유저_로그인_응답_확인(로그인_요청_응답, HttpStatus.UNAUTHORIZED, "U004", "Invalid Username or Password");
    }

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

    /**
     * given: 로그인하 회원이 마이페이지를 보고있다.
     * when: “탈퇴하기”를 버튼을 누르면
     * then: 성공적으로 탈퇴한다.
     */
    @DisplayName("회원은 탈퇴할 수 있다.")
    @Test
    public void user_delete_test() {
        // given
        String accessToken = 로그인_되어_있음(USER_EMAIL, USER_PASSWORD);

        // when
        var 회원_탈퇴_요청 = 회원_탈퇴_요청(accessToken);

        // then
        회원_탈퇴_응답_확인(회원_탈퇴_요청);
    }
}
