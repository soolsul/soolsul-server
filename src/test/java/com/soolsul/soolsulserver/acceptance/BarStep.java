package com.soolsul.soolsulserver.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class BarStep {

    public static ExtractableResponse<Response> findBarFilteredRequest(
            double latitude,
            double longitude,
            String batMoodTagNames,
            String barAlcoholTagNames
    ) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("latitude", latitude)
                .param("longitude", longitude)
                .param("barMoodTagNames", batMoodTagNames)
                .param("barAlcoholTagNames", barAlcoholTagNames)
                .get("/api/bars")
                .then().log().all()
                .extract();
    }

    public static void 바_목록_조회_응답(ExtractableResponse<Response> response) {
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getString("code")).isEqualTo("B001")
        );
    }

}
