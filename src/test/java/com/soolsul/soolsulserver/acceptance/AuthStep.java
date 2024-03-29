package com.soolsul.soolsulserver.acceptance;

import com.soolsul.soolsulserver.user.auth.presentation.dto.UserRegisterRequest;
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
                .body(params)
                .when().post("/api/auth/login")
                .then().log().all()
                .extract();
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

    public static String 회원_정보_조회(ExtractableResponse<Response> response, String email, String name) {
        assertAll(
                () -> assertThat(response.jsonPath().getString("data.userId")).isNotNull(),
                () -> assertThat(response.jsonPath().getString("data.email")).isEqualTo(email),
                () -> assertThat(response.jsonPath().getString("data.name")).isEqualTo(name)
        );
        return response.jsonPath().getString("data.userId");
    }

    public static void 권한_없는_요청(ExtractableResponse<Response> response) {
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value()),
                () -> assertThat(response.jsonPath().getString("code")).isEqualTo("U005")
        );
    }

    public static void 로그아웃_응답_확인(ExtractableResponse<Response> response) {
        assertThat(response.jsonPath().getString("code")).isEqualTo("U007");
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

    public static String 회원_고유_아이디_조회(String accessToken) {
        return 베어러_인증으로_내_회원_정보_조회_요청(accessToken).jsonPath().getString("data.userId");
    }

    public static void 회원_탈퇴_응답_확인(ExtractableResponse<Response> response) {
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getString("code")).isEqualTo("U003")
        );
    }

    public static ExtractableResponse<Response> 회원_탈퇴_요청(String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .when().delete("/api/auth")
                .then().log().all()
                .extract();
    }

    public static void 유저_생성_응답_확인(ExtractableResponse<Response> response, HttpStatus status, String code, String message) {
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(status.value()),
                () -> assertThat(response.jsonPath().getString("code")).isEqualTo(code),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo(message)
        );
    }

    public static ExtractableResponse<Response> 유저_생성_요청(UserRegisterRequest userRegisterRequest) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(userRegisterRequest)
                .when().post("/api/auth/register")
                .then().log().all()
                .extract();
    }

    public static void 유저_로그인_응답_확인(ExtractableResponse<Response> response, HttpStatus status, String code, String message) {
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(status.value()),
                () -> assertThat(response.jsonPath().getString("code")).isEqualTo(code),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo(message)
        );
    }

    public static ExtractableResponse<Response> 사용자_프로필_조회(String accessToken, String userId) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .when()
                .pathParam("userId", userId)
                .get("/api/mypages/{userId}")
                .then().log().all()
                .extract();
    }
}
