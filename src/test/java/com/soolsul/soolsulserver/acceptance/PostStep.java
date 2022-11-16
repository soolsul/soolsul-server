package com.soolsul.soolsulserver.acceptance;

import com.soolsul.soolsulserver.post.presentation.dto.PostCreateRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class PostStep {

    public static void 피드_생성_응답_확인(ExtractableResponse<Response> response) {
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getString("code")).isEqualTo("P001"),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo("피드 생성 성공했습니다.")
        );
    }

    public static ExtractableResponse<Response> 피드_생성_요청(String accessToken, PostCreateRequest postCreateRequest) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(accessToken)
                .body(postCreateRequest)
                .when()
                .post("/api/posts")
                .then().log().all()
                .extract();
    }
}
