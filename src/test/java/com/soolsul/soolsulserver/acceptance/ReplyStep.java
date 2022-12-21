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

    public static void 댓글_추가_요청_응답_확인(ExtractableResponse<Response> 댓글_추가_요청_응답) {
        assertAll(
                () -> assertThat(댓글_추가_요청_응답.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(댓글_추가_요청_응답.jsonPath().getString("code")).isEqualTo("R001")
        );
    }

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
}
