package com.soolsul.soolsulserver.acceptance;

import com.soolsul.soolsulserver.reply.common.dto.request.PostReplyRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ReplyStep {

    public static ExtractableResponse<Response> 피드에_댓글_추가_요청(String accessToken, String 첫_피드_아이디, String reply) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new PostReplyRequest(reply))
                .pathParam("postId", 첫_피드_아이디)
                .when()
                .post("/api/posts/{postId}/replies")
                .then().log().all()
                .extract();
    }

    public static String 댓글_조회_응답_확인(ExtractableResponse<Response> 댓글_조회_요청_응답) {
        assertAll(
                () -> assertThat(댓글_조회_요청_응답.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(댓글_조회_요청_응답.jsonPath().getString("code")).isEqualTo("R002"),
                () -> assertThat(댓글_조회_요청_응답.jsonPath().getString("message")).isEqualTo("댓글을 읽는데 성공하였습니다."),
                () -> assertThat(댓글_조회_요청_응답.jsonPath().getList("data.replies.content").size()).isNotEqualTo(0)
        );
        return 댓글_조회_요청_응답.jsonPath().getString("data.replies.content[0].replyId");
    }

    public static ExtractableResponse<Response> 댓글_조회_요청(String accessToken, String 첫_피드_아이디) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .when()
                .get("/api/posts/{postId}/replies?page={page}&size={size}", 첫_피드_아이디, 0, 1)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 댓글_삭제_요청(String accessToken, String 첫_피드_아이디, String 첫_댓글_아이디) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .when()
                .delete("/api/posts/{postId}/replies/{replyId}", 첫_피드_아이디, 첫_댓글_아이디)
                .then().log().all()
                .extract();
    }
}
