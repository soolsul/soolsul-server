package com.soolsul.soolsulserver.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class MyPageStep {

    public static void 스크랩_피드_응답_확인(ExtractableResponse<Response> response) {
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getString("code")).isEqualTo("P006"),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo("모든 스크랩 된 피드를 찾는데 성공하였습니다."),
                () -> assertThat(response.jsonPath().getList("data.postList").size()).isNotEqualTo(0)
        );
    }

    public static ExtractableResponse<Response> 스크랩_피드_조회_요청(String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .when()
                .get("/api/mypages/scraps")
                .then().log().all()
                .extract();
    }
}
