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

@Disabled
public class PostAcceptanceTest extends AcceptanceTest {

    // TODO : RestAsured Auth 설정도 함께 해야 동작하는데, 아직 로그인이 없어 설정하지 않았다.

    /**
     * given: 사용자가 로그인 한 상태이다.
     * and: 해당 가게가 이미 등록되어 있다.
     * and: 사용자가 본문을 작성하였다.
     * when: 작성 완료를 누르면
     * then: 성공적으로 저장되고, 피드 리스트 페이지로 이동한다.
     */
    @DisplayName("사용자가 피드를 작성후 작성 완료를 누르는 경우 서버에 정상저장 된다.")
    @Test
    public void create_post_test() {
        // given
        List<String> imagesUrl = List.of("url1", "url2", "url3");
        List<String> tags = List.of("mood_tag1", "mood_tag2", "alcohol_tag1");
        PostCreateRequest postCreateRequest = new PostCreateRequest(STORE_UUID, "본문 내용 입니다", 4.3f, LocalDate.now(), imagesUrl, tags);

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(postCreateRequest)
                .when()
                .post("/posts")
                .then().log().all()
                .extract();

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getString("code")).isEqualTo("P001"),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo("피드 생성 성공했습니다.")
        );
    }

    /**
     * given: 사용자가 로그인 한 상태이다.
     * and: 사전에 등록된 피드들이 있으며
     * and: 사용자가 피드 목록을 보고 있다.
     * when: User가 리스트에서 특정 가게의 피드을 누른다.
     * then: 해당 피드로 이동한다.
     */
    @DisplayName("사용자가 피드 목록에서 특정 피드를 누르면 해당 단일 피드를 보여준다.")
    @Test
    public void find_post_detail_test() {
        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/posts/{postId}", 1)
                .then().log().all()
                .extract();

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getString("code")).isEqualTo("P002"),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo("피드 상세 조회 성공했습니다.")
        );
    }

    /**
     * given: 사용자가 로그인 한 상태이다.
     * and: 사용자가 첫 지도화면에 있는 상태이다.
     * when: 네비게이션의 피드를 누른다.
     * then: 피드 리스트 페이지로 이동한다.
     */
    @DisplayName("사용자가 하단 네비게이션에서 피드 선택시 피드 목록이 보여진다")
    @Test
    public void find_post_list_test() {
        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/posts")
                .then().log().all()
                .extract();

        // TODO: 리스트 본문 검증 테스트 아직 미구현
        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getString("code")).isEqualTo("P004"),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo("모든 피드를 찾는데 성공하였습니다.")
        );
    }
}
