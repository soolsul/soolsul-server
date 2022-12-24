package com.soolsul.soolsulserver.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class CommonStep {

    public static void 응답_확인(ExtractableResponse<Response> response, HttpStatus status, String code) {
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(status.value()),
                () -> assertThat(response.jsonPath().getString("code")).isEqualTo(code)
        );
    }
}
