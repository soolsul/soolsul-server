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

    public static String 피드_조회_응답_확인(ExtractableResponse<Response> response) {
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getString("code")).isEqualTo("P004"),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo("모든 피드를 찾는데 성공하였습니다."),
                () -> assertThat(response.jsonPath().getList("data.postList").size()).isNotEqualTo(0)
        );
        return response.jsonPath().getString("data.postList[0].postId");
    }

    public static ExtractableResponse<Response> 피드_목록_조회_요청(String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .when()
                .pathParam("latitude", 37.49909732361135d)
                .pathParam("longitude", 126.9459247225818d)
                .pathParam("level", 5)
                .get("/api/posts?latitude={latitude}&longitude={longitude}&level={level}")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 피드_단건_조회_요청(String accessToken, String 첫_피드_아이디) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .when()
                .get("/api/posts/{postId}", 첫_피드_아이디)
                .then().log().all()
                .extract();
    }

    public static void 피드_단건_조회_응답_확인(ExtractableResponse<Response> 피드_단건_조회_응답) {
        assertAll(
                () -> assertThat(피드_단건_조회_응답.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(피드_단건_조회_응답.jsonPath().getString("code")).isEqualTo("P002"),
                () -> assertThat(피드_단건_조회_응답.jsonPath().getString("message")).isEqualTo("피드 찾기에 성공하였습니다.")
        );
    }
}
