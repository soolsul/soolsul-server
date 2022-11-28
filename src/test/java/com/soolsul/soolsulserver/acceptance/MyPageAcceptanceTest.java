package com.soolsul.soolsulserver.acceptance;

import com.soolsul.soolsulserver.user.mypage.presentation.dto.reqeust.UserInfoEditRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.soolsul.soolsulserver.acceptance.AuthStep.로그인_되어_있음;
import static com.soolsul.soolsulserver.acceptance.MyPageStep.사용자_댓글_조회_응답_확인;
import static com.soolsul.soolsulserver.acceptance.MyPageStep.사용자_피드_조회_응답_확인;
import static com.soolsul.soolsulserver.acceptance.MyPageStep.사용자가_작성한_피드_목록_조회;
import static com.soolsul.soolsulserver.acceptance.MyPageStep.사용자가_추가한_댓글_목록_조회;
import static com.soolsul.soolsulserver.acceptance.MyPageStep.스크랩_피드_응답_확인;
import static com.soolsul.soolsulserver.acceptance.MyPageStep.스크랩_피드_조회_요청;
import static com.soolsul.soolsulserver.acceptance.PostStep.피드_목록_조회_요청;
import static com.soolsul.soolsulserver.acceptance.PostStep.피드_생성_요청;
import static com.soolsul.soolsulserver.acceptance.PostStep.피드_생성_정보_생성;
import static com.soolsul.soolsulserver.acceptance.PostStep.피드_스크랩_요청;
import static com.soolsul.soolsulserver.acceptance.PostStep.피드_조회_응답_확인;
import static com.soolsul.soolsulserver.acceptance.ReplyStep.피드에_댓글_추가_요청;
import static com.soolsul.soolsulserver.common.data.DataLoader.postIdOne;
import static com.soolsul.soolsulserver.common.data.DataLoader.postIdTwo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class MyPageAcceptanceTest extends AcceptanceTest {

    /**
     * given: 등록된 피드가 있고
     * and: 사전에 스크랩된 피드 목록이 있다.
     * and: 마이페이지를 보고있는 User가 있다.
     * when: “저장함” 버튼을 누르면
     * then: 저장함에 저장된 피드 리스트가 뜬다.
     */
    @DisplayName("스크랩된 피드를 전부 가져온다.")
    @Test
    public void scraped_post_find_all_test() {
        // given
        String accessToken = 로그인_되어_있음(USER_EMAIL, USER_PASSWORD);
        피드_스크랩_요청(accessToken, postIdOne);
        피드_스크랩_요청(accessToken, postIdTwo);

        // when
        var 스크랩_피드_응답 = 스크랩_피드_조회_요청(accessToken);

        // then
        스크랩_피드_응답_확인(스크랩_피드_응답);
    }

    /**
     * given: 사용자가 작성해둔 피드들이 있고
     * when: 내 게시물 조회 요청시
     * then: 내가 작성한 게시물을 전부 조회해온다.
     */
    @DisplayName("사용자가 작성한 피드를 전부 가져온다.")
    @Test
    public void my_post_find_all_test() {
        // given
        String accessToken = 로그인_되어_있음(USER_EMAIL, USER_PASSWORD);
        피드_생성_요청(accessToken, 피드_생성_정보_생성());

        // when
        ExtractableResponse<Response> 사용자_피드_조회_응답 = 사용자가_작성한_피드_목록_조회(accessToken);

        // then
        사용자_피드_조회_응답_확인(사용자_피드_조회_응답);
    }

    /**
     * given: 사용자가 작성해둔 피드드와 추가된 댓글이 있고
     * when: 내 댓글 조회 요청시
     * then: 내가 추가한 댓글 목록을 전부 조회해온다.
     */
    @DisplayName("사용자가 작성한 피드를 전부 가져온다.")
    @Test
    public void my_reply_find_all_test() {
        // given
        String accessToken = 로그인_되어_있음(USER_EMAIL, USER_PASSWORD);
        피드_생성_요청(accessToken, 피드_생성_정보_생성());
        var 피드_목록_조회_응답 = 피드_목록_조회_요청(accessToken);
        String 첫_피드_아이디 = 피드_조회_응답_확인(피드_목록_조회_응답);
        피드에_댓글_추가_요청(accessToken, 첫_피드_아이디, "댓글 추가요 1");
        피드에_댓글_추가_요청(accessToken, 첫_피드_아이디, "댓글 추가요 2");
        피드에_댓글_추가_요청(accessToken, 첫_피드_아이디, "댓글 추가요 3");

        // when
        ExtractableResponse<Response> 사용자_댓글_조회_응답 = 사용자가_추가한_댓글_목록_조회(accessToken);

        // then
        사용자_댓글_조회_응답_확인(사용자_댓글_조회_응답);
    }

    /**
     * given: 가입후 로그인한 사용자가 있고
     * when: 사용자가 프로필 편집을 누르면
     * then: 기존의 사용자 이미지, 닉네임, 이메일을 반환한다
     * when: 사용자가 프로필을 수정후 전송하면
     * then: 수정된 정보가 서버에 저장된다
     */
    @DisplayName("사용자의 상세 정보를 반환한다.")
    @Test
    public void my_info_find_test() {
        // given
        String accessToken = 로그인_되어_있음(USER_EMAIL, USER_PASSWORD);

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .when()
                .get("/api/mypages/edit")
                .then().log().all()
                .extract();

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getString("code")).isEqualTo("M003"),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo("유저의 기본 정보 조회에 성공했습니다."),
                () -> assertThat(response.jsonPath().getString("data.imageUrl")).isNotBlank(),
                () -> assertThat(response.jsonPath().getString("data.nickName")).isEqualTo(NICK_NAME),
                () -> assertThat(response.jsonPath().getString("data.email")).isEqualTo(USER_EMAIL)
        );

        // when
        ExtractableResponse<Response> editRequest = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new UserInfoEditRequest("new_url", "change", "change@email.com"))
                .when()
                .patch("/api/mypages/edit")
                .then().log().all()
                .extract();

        // then
        assertAll(
                () -> assertThat(editRequest.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(editRequest.jsonPath().getString("code")).isEqualTo("M004"),
                () -> assertThat(editRequest.jsonPath().getString("message")).isEqualTo("유저의 기본 정보 수정에 성공했습니다.")
        );
    }
}
