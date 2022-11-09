package com.soolsul.soolsulserver.acceptance;

import com.soolsul.soolsulserver.post.presentation.dto.PostCreateRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class PostAcceptanceTest extends AcceptanceTest {

    /**
     * given: 해당 가게가 이미 등록되어 있다.
     * and: 사용자가 본문을 작성하였다.
     * when: 작성 완료를 누르면
     * then: 성공적으로 저장되고, 피드 리스트 페이지로 이동한다.
     */
    @DisplayName("사용자가 피드를 작성후 작성 완료를 누르는 경우 서버에 정상저장 된다.")
    @Disabled
    @Test
    public void create_post_test() {
        // given
        List<String> imagesUrl = List.of("url1", "url2", "url3");
        List<String> tags = List.of("tag1", "tag2");
        PostCreateRequest postCreateRequest = new PostCreateRequest(STORE_UUID, "본문 내용 입니다", 4.3f, LocalDate.now(), imagesUrl, tags);

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(postCreateRequest)
                .when()
                .post("/api/posts")
                .then().log().all()
                .extract();

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getString("code")).isEqualTo("P001"),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo("피드 생성 성공했습니다.")
        );
    }
}
