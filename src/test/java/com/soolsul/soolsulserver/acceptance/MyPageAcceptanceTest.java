package com.soolsul.soolsulserver.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static com.soolsul.soolsulserver.acceptance.AuthStep.로그인_되어_있음;
import static com.soolsul.soolsulserver.acceptance.PostStep.피드_스크랩_요청;
import static com.soolsul.soolsulserver.common.data.DataLoader.postId;
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
        피드_스크랩_요청(accessToken, postId);

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .when()
                .get("/api/auth/me/scraps")
                .then().log().all()
                .extract();

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getString("code")).isEqualTo("P006"),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo("모든 스크랩 된 피드를 찾는데 성공하였습니다."),
                () -> assertThat(response.jsonPath().getList("data.postList").size()).isNotEqualTo(0)
        );
    }
}
