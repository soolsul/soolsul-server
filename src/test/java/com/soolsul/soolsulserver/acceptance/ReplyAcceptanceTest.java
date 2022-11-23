package com.soolsul.soolsulserver.acceptance;

import com.soolsul.soolsulserver.reply.presentation.dto.request.PostReplyRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.soolsul.soolsulserver.acceptance.AuthStep.로그인_되어_있음;
import static com.soolsul.soolsulserver.acceptance.PostStep.피드_목록_조회_요청;
import static com.soolsul.soolsulserver.acceptance.PostStep.피드_생성_요청;
import static com.soolsul.soolsulserver.acceptance.PostStep.피드_생성_정보_생성;
import static com.soolsul.soolsulserver.acceptance.PostStep.피드_조회_응답_확인;
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
    public void add_reply_test() {
        // given
        String accessToken = 로그인_되어_있음(USER_EMAIL, USER_PASSWORD);
        피드_생성_요청(accessToken, 피드_생성_정보_생성());
        var 피드_목록_조회_응답 = 피드_목록_조회_요청(accessToken);
        String 첫_피드_아이디 = 피드_조회_응답_확인(피드_목록_조회_응답);

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new PostReplyRequest("댓글 추가요!"))
                .pathParam("postId", 첫_피드_아이디)
                .when()
                .post("/api/posts/{postId}/replies")
                .then().log().all()
                .extract();

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getString("code")).isEqualTo("R001"),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo("댓글을 추가하는데 성공하였습니다.")
        );
    }
}
