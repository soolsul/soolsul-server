package com.soolsul.soolsulserver.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class AuthStep {

    public static ExtractableResponse<Response> 로그인_요청(String email, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);

        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("X-Requested-With", "JSONLoginHttpRequest")
                .body(params)
                .when().post("/api/login")
                .then().log().all()
                .statusCode(HttpStatus.OK.value()).extract();
    }

    public static String 로그인_되어_있음(String email, String password) {
        ExtractableResponse<Response> response = 로그인_요청(email, password);
        return response.jsonPath().getString("data.accessToken");
    }

    public static ExtractableResponse<Response> 베어러_인증으로_내_회원_정보_조회_요청(String accessToken) {
        return RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/mypages/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();
    }

    public static void 회원_정보_조회(ExtractableResponse<Response> response, String email, String name) {
        assertAll(
                () -> assertThat(response.jsonPath().getString("data.userId")).isNotNull(),
                () -> assertThat(response.jsonPath().getString("data.email")).isEqualTo(email),
                () -> assertThat(response.jsonPath().getString("data.name")).isEqualTo(name)
        );
    }

    public static void 권한_없는_요청(ExtractableResponse<Response> 피드_목록_조회_응답) {
        assertAll(
                () -> assertThat(피드_목록_조회_응답.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value()),
                () -> assertThat(피드_목록_조회_응답.jsonPath().getString("code")).isEqualTo("U005"),
                () -> assertThat(피드_목록_조회_응답.jsonPath().getString("message")).isEqualTo("해당 유저는 권한이 없습니다.")
        );
    }

    public static void 로그아웃_응답_확인(ExtractableResponse<Response> response) {
        assertAll(
                () -> assertThat(response.jsonPath().getString("code")).isEqualTo("U007"),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo("로그아웃에 성공하였습니다.")
        );
    }

    public static ExtractableResponse<Response> 로그아웃_요청(String accessToken) {
        return RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/auth/logout")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();
    }
}
