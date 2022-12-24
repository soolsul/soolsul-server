package com.soolsul.soolsulserver.acceptance;

import com.soolsul.soolsulserver.post.common.dto.request.PostCreateRequest;
import com.soolsul.soolsulserver.post.common.dto.request.PostScrapRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.soolsul.soolsulserver.data.DataLoader.barId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class PostStep {

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

    public static ExtractableResponse<Response> 피드_스크랩_요청(String accessToken, String 첫_피드_아이디) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new PostScrapRequest(첫_피드_아이디))
                .when()
                .post("/api/posts/scraps")
                .then().log().all()
                .extract();
    }

    public static PostCreateRequest 피드_생성_정보_생성() {
        List<String> imagesUrl = List.of("url1", "url2", "url3");
        List<String> tags = List.of("mood_tag1", "mood_tag2", "alcohol_tag1");
        LocalDate date = LocalDate.parse(LocalDate.now().toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        PostCreateRequest postCreateRequest = new PostCreateRequest(barId, "본문 내용 입니다", 4.3f, date, imagesUrl, tags);
        return postCreateRequest;
    }

    public static ExtractableResponse<Response> 피드_삭제_요청(String accessToken, String 첫_피드_아이디) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .when()
                .delete("/api/posts/{postId}", 첫_피드_아이디)
                .then().log().all()
                .extract();
    }
}
