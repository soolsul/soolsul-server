package com.soolsul.soolsulserver.documentation;

import com.soolsul.soolsulserver.post.common.dto.request.PostCreateRequest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

import static com.soolsul.soolsulserver.acceptance.AuthStep.로그인_되어_있음;
import static com.soolsul.soolsulserver.acceptance.PostStep.피드_생성_정보_생성;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

public class PostDocumentation extends Documentation {

    @DisplayName("문서화 : Post 생성")
    @Test
    void create_post_success() throws Exception {
        String accessToken = 로그인_되어_있음(USER_EMAIL, USER_PASSWORD);

        PostCreateRequest postCreateRequest = 피드_생성_정보_생성();

        RestAssured
                .given(spec).log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .body(postCreateRequest)
                .filter(
                        document("create-post",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                createPostRequestBody(),
                                postResponseBody())
                )
                .when().post("/api/posts")
                .then().log().all()
                .extract();
    }

    private Snippet createPostRequestBody() {
        return requestFields(
                fieldWithPath("barId").type(JsonFieldType.STRING).description("가게 아이디"),
                fieldWithPath("postContent").type(JsonFieldType.STRING).description("리뷰 글 내용"),
                fieldWithPath("score").type(JsonFieldType.NUMBER).description("리뷰 평점"),
                fieldWithPath("visitedDate").type(JsonFieldType.ARRAY).description("가게 방문일"),
                fieldWithPath("images").type(JsonFieldType.ARRAY).description("리뷰를 위한 사진 URL"),
                fieldWithPath("tags").type(JsonFieldType.ARRAY).description("리뷰에 포함된 태그 목록"));
    }

    private Snippet postResponseBody() {
        return responseFields(
                fieldWithPath("code").description(Constants.RESPONSE_ID),
                fieldWithPath("message").description(Constants.RESPONSE_MESSAGE),
                fieldWithPath("data").description(Constants.RESPONSE_DATA).optional());
    }

    private static class Constants {
        private static final String RESPONSE_ID = "응답 상태 코드";
        private static final String RESPONSE_MESSAGE = "응답 메시지";
        private static final String RESPONSE_DATA = "응답 데이터";
    }
}
