package com.soolsul.soolsulserver.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static com.soolsul.soolsulserver.acceptance.AuthStep.로그인_되어_있음;
import static com.soolsul.soolsulserver.acceptance.PostStep.피드_목록_조회_요청;
import static com.soolsul.soolsulserver.acceptance.PostStep.피드_생성_요청;
import static com.soolsul.soolsulserver.acceptance.PostStep.피드_생성_정보_생성;
import static com.soolsul.soolsulserver.acceptance.PostStep.피드_조회_응답_확인;
import static com.soolsul.soolsulserver.acceptance.ReplyStep.댓글_추가_요청_응답_확인;
import static com.soolsul.soolsulserver.acceptance.ReplyStep.피드에_댓글_추가_요청;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ReplyAcceptanceTest extends AcceptanceTest {

    /**
     * given: 피드가 하나 생성되어 있다.
     * and: 사용자가 피드 단건을 보고 있다.
     * when: 특정 피드에 댓글을 추가한다.
     * then: 정상적으로 댓글이 추가된다.
     */
    @DisplayName("Post에 댓글을 추가할 수 있다.")
    @Test
    void add_reply_test() {
        // given
        String accessToken = 로그인_되어_있음(USER_EMAIL, USER_PASSWORD);
        피드_생성_요청(accessToken, 피드_생성_정보_생성());
        var 피드_목록_조회_응답 = 피드_목록_조회_요청(accessToken);
        String 첫_피드_아이디 = 피드_조회_응답_확인(피드_목록_조회_응답);

        // when
        ExtractableResponse<Response> 댓글_추가_요청_응답 = 피드에_댓글_추가_요청(accessToken, 첫_피드_아이디, "댓글 추가요!");

        // then
        댓글_추가_요청_응답_확인(댓글_추가_요청_응답);
    }

    /**
     * given: 피드가 하나 생성되어 있다.
     * when: 사용자가 피드 단건을 보고 있다.
     * then: 추가된 댓글도 함께 보인다.
     */
    @DisplayName("Post에 추가된 댓글을 확인할 수 있다.")
    @Test
    void find_all_replies_test() {
        // given
        String accessToken = 로그인_되어_있음(USER_EMAIL, USER_PASSWORD);
        피드_생성_요청(accessToken, 피드_생성_정보_생성());
        var 피드_목록_조회_응답 = 피드_목록_조회_요청(accessToken);
        String 첫_피드_아이디 = 피드_조회_응답_확인(피드_목록_조회_응답);
        피드에_댓글_추가_요청(accessToken, 첫_피드_아이디, "댓글 추가요!");

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .when()
                .get("/api/posts/{postId}/replies?page={page}&size={size}", 첫_피드_아이디, 0, 1)
                .then().log().all()
                .extract();

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getString("code")).isEqualTo("R002"),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo("댓글을 읽는데 성공하였습니다."),
                () -> assertThat(response.jsonPath().getList("data.replies.content").size()).isNotEqualTo(0)
        );
    }
}
