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

    public static void 사용자_피드_조회_응답_확인(ExtractableResponse<Response> 사용자_피드_조회_응답) {
        assertAll(
                () -> assertThat(사용자_피드_조회_응답.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(사용자_피드_조회_응답.jsonPath().getString("code")).isEqualTo("M001"),
                () -> assertThat(사용자_피드_조회_응답.jsonPath().getString("message")).isEqualTo("유저의 피드 조회에 성공했습니다."),
                () -> assertThat(사용자_피드_조회_응답.jsonPath().getList("data.postList").size()).isNotEqualTo(0)
        );
    }

    public static ExtractableResponse<Response> 사용자가_작성한_피드_목록_조회(String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .when().get("/api/mypages/posts")
                .then().log().all()
                .extract();
    }

    public static void 사용자_댓글_조회_응답_확인(ExtractableResponse<Response> response) {
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getString("code")).isEqualTo("M002"),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo("유저의 댓글 조회에 성공했습니다."),
                () -> assertThat(response.jsonPath().getList("data.replyList").size()).isEqualTo(3),
                () -> assertThat(response.jsonPath().getList("data.replyList")).extracting("contents").containsExactly("댓글 추가요 1", "댓글 추가요 2", "댓글 추가요 3")
        );
    }

    public static ExtractableResponse<Response> 사용자가_추가한_댓글_목록_조회(String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .when().get("/api/mypages/replies")
                .then().log().all()
                .extract();
    }
}
