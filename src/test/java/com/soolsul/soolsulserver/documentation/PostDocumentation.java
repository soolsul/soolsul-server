package com.soolsul.soolsulserver.documentation;

import com.soolsul.soolsulserver.post.common.dto.request.PostCreateRequest;
import com.soolsul.soolsulserver.post.common.dto.response.PostDetailLikeResponse;
import com.soolsul.soolsulserver.post.common.dto.response.PostDetailResponse;
import com.soolsul.soolsulserver.post.common.dto.response.PostDetailStoreResponse;
import com.soolsul.soolsulserver.post.common.dto.response.PostDetailUserResponse;
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
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
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

        doNothing().when(postFacadeGateway).create(any(), any());

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
                                createPostResponseBody())
                );
    }

    @DisplayName("문서화 : Post 단건 조회")
    @WithMockUser
    @Test
    void find_post_success() throws Exception {
        List<String> imagesUrl = List.of("url1", "url2", "url3");
        PostDetailResponse postDetailResponse = new PostDetailResponse(
                "post_id", 4.3f, "contents", imagesUrl,
                new PostDetailLikeResponse(42, true),
                new PostDetailUserResponse("userId", "nick name", "profile_url"),
                new PostDetailStoreResponse("store_id", "name", "this is store")
        );

        given(postFacadeGateway.find(any(), anyString())).willReturn(postDetailResponse);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/posts/{postId}", "post_uuid")
                        .header("Authorization", "bearer login-jwt-token")
                        .accept(MediaTypes.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("find-post",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                findPostRequestParam(),
                                findPostResponseBody())
                );
    }

    private Snippet findPostResponseBody() {
        return responseFields(
                fieldWithPath("code").type(JsonFieldType.STRING).description(Constants.RESPONSE_ID),
                fieldWithPath("message").type(JsonFieldType.STRING).description(Constants.RESPONSE_MESSAGE),
                fieldWithPath("data").type(JsonFieldType.OBJECT).description(Constants.RESPONSE_DATA).optional(),
                fieldWithPath("data.postId").type(JsonFieldType.STRING).description("피드 ID"),
                fieldWithPath("data.score").type(JsonFieldType.NUMBER).description("피드에 남긴 가게 평점"),
                fieldWithPath("data.contents").type(JsonFieldType.STRING).description("피드 본문"),
                fieldWithPath("data.imageUrls").type(JsonFieldType.ARRAY).description("피드에 게시된 사진들"),
                fieldWithPath("data.like.count").type(JsonFieldType.NUMBER).description("피드 좋아요 수"),
                fieldWithPath("data.like.userLikeStatus").type(JsonFieldType.BOOLEAN).description("피드에 사용자가 좋아요를 눌렀는지"),
                fieldWithPath("data.user.userId").type(JsonFieldType.STRING).description("피드 작성자 ID"),
                fieldWithPath("data.user.userNickname").type(JsonFieldType.STRING).description("피드 작성자 별칭"),
                fieldWithPath("data.user.userProfileUrl").type(JsonFieldType.STRING).description("피드 작성자 이미지 주소"),
                fieldWithPath("data.store.storeId").type(JsonFieldType.STRING).description("가게 ID"),
                fieldWithPath("data.store.storeName").type(JsonFieldType.STRING).description("가게 이름"),
                fieldWithPath("data.store.description").type(JsonFieldType.STRING).description("가게 설명")

        );
    }

    private Snippet findPostRequestParam() {
        return pathParameters(
                parameterWithName("postId").description("리뷰 ID")
        );
    }

    private Snippet createPostRequestBody() {
        return requestFields(
                fieldWithPath("barId").type(JsonFieldType.STRING).description("가게 아이디"),
                fieldWithPath("postContent").type(JsonFieldType.STRING).description("리뷰 글 내용"),
                fieldWithPath("score").type(JsonFieldType.NUMBER).description("리뷰 평점"),
                fieldWithPath("visitedDate").type(JsonFieldType.STRING).description("가게 방문일"),
                fieldWithPath("images").type(JsonFieldType.ARRAY).description("리뷰를 위한 사진 URL"),
                fieldWithPath("tags").type(JsonFieldType.ARRAY).description("리뷰에 포함된 태그 목록"));
    }

    private Snippet createPostResponseBody() {
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
