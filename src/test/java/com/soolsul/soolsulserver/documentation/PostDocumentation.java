package com.soolsul.soolsulserver.documentation;

import com.soolsul.soolsulserver.post.common.dto.request.PostCreateRequest;
import com.soolsul.soolsulserver.post.facade.PostFacadeGateway;
import com.soolsul.soolsulserver.post.presentation.PostController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.spec.internal.MediaTypes;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PostController.class,
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfigurer.class)},
        excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class PostDocumentation extends Documentation {

    @MockBean
    PostFacadeGateway postFacadeGateway;

    @DisplayName("문서화 : Post 생성")
    @WithMockUser
    @Test
    void create_post_success() throws Exception {
        List<String> imagesUrl = List.of("url1", "url2", "url3");
        List<String> tags = List.of("mood_tag1", "mood_tag2", "alcohol_tag1");
        LocalDate date = LocalDate.parse(LocalDate.now().toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        PostCreateRequest postCreateRequest = new PostCreateRequest("barId", "본문 내용 입니다", 4.3f, date, imagesUrl, tags);

        doNothing().when(postFacadeGateway).create(anyString(), any());

        mockMvc.perform(post("/api/posts/")
                        .header("Authorization", "bearer login-jwt-token")
                        .contentType(MediaTypes.APPLICATION_JSON)
                        .accept(MediaTypes.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postCreateRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("create-post",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                createPostRequestBody(),
                                postResponseBody())
                );

    }

//    @DisplayName("문서화 : Post 생성")
//    @Test
//    void create_post_success() throws Exception {
//        String accessToken = 로그인_되어_있음(USER_EMAIL, USER_PASSWORD);
//
//        PostCreateRequest postCreateRequest = 피드_생성_정보_생성();
//
//        RestAssured
//                .given(spec).log().all()
//                .auth().oauth2(accessToken)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .accept(MediaType.APPLICATION_JSON_VALUE)
//                .body(postCreateRequest)
//                .filter(
//                        document("create-post",
//                                preprocessRequest(prettyPrint()),
//                                preprocessResponse(prettyPrint()),
//                                createPostRequestBody(),
//                                postResponseBody())
//                )
//                .when().post("/api/posts")
//                .then().log().all()
//                .extract();
//    }

//    @DisplayName("문서화 : Post 단건 조회")
//    @Test
//    void find_post_success() {
//        String accessToken = 로그인_되어_있음(USER_EMAIL, USER_PASSWORD);
//
//        RestAssured
//                .given(spec).log().all()
//                .auth().oauth2(accessToken)
//                .accept(MediaType.APPLICATION_JSON_VALUE)
//                .filter(
//                        document("find-post",
//                                preprocessRequest(prettyPrint()),
//                                preprocessResponse(prettyPrint()),
//                                postResponseBody()
//                        )
//                )
//                .when().get("/api/posts/{postId}", DataLoader.postIdOne)
//                .then().log().all()
//                .extract();
//    }

    private Snippet createPostRequestBody() {
        return requestFields(
                fieldWithPath("barId").type(JsonFieldType.STRING).description("가게 아이디"),
                fieldWithPath("postContent").type(JsonFieldType.STRING).description("리뷰 글 내용"),
                fieldWithPath("score").type(JsonFieldType.NUMBER).description("리뷰 평점"),
                fieldWithPath("visitedDate").type(JsonFieldType.STRING).description("가게 방문일"),
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

        private static final String ID_DESCRIPTION = "가게 아이디";
        private static final String STORE_NAME_DESCRIPTION = "가게 이름";
        private static final String CONTACT_NUMBER_DESCRIPTION = "가게 연락처";
        private static final String ADDRESS_NAME_DESCRIPTION = "지번 주소";
        private static final String ROAD_ADDRESS_NAME_DESCRIPTION = "도로명 주소";
        private static final String Y_DESCRIPTION = "위도(최대값: 90.0 / 최소값: -90.0)";
        private static final String X_DESCRIPTION = "경도(최대값: 180.0 / 최소값: -180.0)";
        private static final String REVIEW_SCORE_AVERAGE = "가게의 리뷰 평균 점수 입니다.";
        private static final String REVIEW_COUNTS = "가게의 리뷰 개수입니다.";
    }
}
