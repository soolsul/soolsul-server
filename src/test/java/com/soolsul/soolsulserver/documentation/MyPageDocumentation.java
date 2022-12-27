package com.soolsul.soolsulserver.documentation;

import com.soolsul.soolsulserver.post.common.dto.response.ScrapedPostLookUpResponse;
import com.soolsul.soolsulserver.post.common.dto.response.UserPostLookUpResponse;
import com.soolsul.soolsulserver.post.common.dto.response.UserReplyLookUpResponse;
import com.soolsul.soolsulserver.user.auth.persistence.dto.response.UserLookUpResponse;
import com.soolsul.soolsulserver.user.mypage.common.dto.response.ScrapedPostListLookUpResponse;
import com.soolsul.soolsulserver.user.mypage.common.dto.response.UserPostListLookUpResponse;
import com.soolsul.soolsulserver.user.mypage.common.dto.response.UserReplyListLookUpResponse;
import com.soolsul.soolsulserver.user.mypage.facade.MyPageCommandFacade;
import com.soolsul.soolsulserver.user.mypage.facade.MyPageQueryFacade;
import com.soolsul.soolsulserver.user.mypage.presentation.MyPageController;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MyPageController.class,
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfigurer.class)},
        excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class MyPageDocumentation extends Documentation {

    @MockBean
    MyPageQueryFacade myPageQueryFacade;

    @MockBean
    MyPageCommandFacade myPageCommandFacade;

    @DisplayName("문서화 : 유저 정보 상세조회")
    @Test
    public void find_user_detail_info() throws Exception {
        UserLookUpResponse userLookUpResponse = new UserLookUpResponse(
                "ff808081854e558801854e559bbe0003",
                "change@email.com",
                "{bcrypt}$2a$10$aCz2zAlS/o.5csrY/7CJkuBY91tvo5.4b3BES.tcwQtu.hKQ5HA1a",
                "02-123-4567",
                "user",
                "change",
                "new_url"
        );

        given(myPageQueryFacade.findUserWithDetailInfo(any())).willReturn(userLookUpResponse);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/mypages/me")
                        .header("Authorization", "bearer login-jwt-token")
                        .accept(MediaTypes.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(
                        document("search-detail-info-mypage",
                                lookupMyPageResponseBody())
                );
    }

    @DisplayName("문서화 : 사용자 작성 리뷰 전체 조회")
    @Test
    public void find_all_post() throws Exception {
        UserPostLookUpResponse postOne = new UserPostLookUpResponse("post_id_1", "url_1");
        UserPostLookUpResponse postTwo = new UserPostLookUpResponse("post_id_2", "url_2");
        UserPostListLookUpResponse postListLookUpResponse = new UserPostListLookUpResponse(List.of(postOne, postTwo));

        given(myPageQueryFacade.findAllUserPost(any())).willReturn(postListLookUpResponse);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/mypages/posts")
                        .header("Authorization", "bearer login-jwt-token")
                        .accept(MediaTypes.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(
                        document("search-posts-mypage",
                                lookupPostListResponseBody())
                );
    }

    @DisplayName("문서화 : 사용자 작성 댓글 전체 조회")
    @Test
    public void find_all_reply() throws Exception {
        UserReplyLookUpResponse replyOne = new UserReplyLookUpResponse("post_id_1", "댓글 1");
        UserReplyLookUpResponse replyTwo = new UserReplyLookUpResponse("post_id_1", "댓글 2");
        UserReplyListLookUpResponse replyListLookUpResponse = new UserReplyListLookUpResponse(List.of(replyOne, replyTwo));

        given(myPageQueryFacade.findAllUserReplies(any())).willReturn(replyListLookUpResponse);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/mypages/replies")
                        .header("Authorization", "bearer login-jwt-token")
                        .accept(MediaTypes.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(
                        document("search-replies-mypage",
                                lookupReplyListResponseBody())
                );
    }

    @DisplayName("문서화 : 사용자 스크랩한 피드 전체 조회")
    @Test
    public void find_all_scrap() throws Exception {
        ScrapedPostLookUpResponse scrapOne = new ScrapedPostLookUpResponse("post_id_1", "image_url_1");
        ScrapedPostLookUpResponse scrapTwo = new ScrapedPostLookUpResponse("post_id_2", "image_url_2");
        ScrapedPostListLookUpResponse scrapedPostListLookUpResponse = new ScrapedPostListLookUpResponse(List.of(scrapOne, scrapTwo));

        given(myPageQueryFacade.findAllScrapedPost(any())).willReturn(scrapedPostListLookUpResponse);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/mypages/scraps")
                        .header("Authorization", "bearer login-jwt-token")
                        .accept(MediaTypes.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(
                        document("search-scraps-mypage",
                                lookupScrapListResponseBody())
                );
    }

    private Snippet lookupScrapListResponseBody() {
        return responseFields(
                fieldWithPath("code").type(JsonFieldType.STRING).description(Constants.RESPONSE_ID),
                fieldWithPath("message").type(JsonFieldType.STRING).description(Constants.RESPONSE_MESSAGE),
                fieldWithPath("data").type(JsonFieldType.OBJECT).description(Constants.RESPONSE_DATA).optional(),
                fieldWithPath("data.postList[].postId").type(JsonFieldType.STRING).description("피드 ID"),
                fieldWithPath("data.postList[].imageUrl").type(JsonFieldType.STRING).description("피드 대표 사진")
        );
    }

    private Snippet lookupReplyListResponseBody() {
        return responseFields(
                fieldWithPath("code").type(JsonFieldType.STRING).description(Constants.RESPONSE_ID),
                fieldWithPath("message").type(JsonFieldType.STRING).description(Constants.RESPONSE_MESSAGE),
                fieldWithPath("data").type(JsonFieldType.OBJECT).description(Constants.RESPONSE_DATA).optional(),
                fieldWithPath("data.replyList[].postId").type(JsonFieldType.STRING).description("피드 ID"),
                fieldWithPath("data.replyList[].contents").type(JsonFieldType.STRING).description("댓글 내용")
        );
    }

    private Snippet lookupPostListResponseBody() {
        return responseFields(
                fieldWithPath("code").type(JsonFieldType.STRING).description(Constants.RESPONSE_ID),
                fieldWithPath("message").type(JsonFieldType.STRING).description(Constants.RESPONSE_MESSAGE),
                fieldWithPath("data").type(JsonFieldType.OBJECT).description(Constants.RESPONSE_DATA).optional(),
                fieldWithPath("data.postList[].postId").type(JsonFieldType.STRING).description("피드 ID"),
                fieldWithPath("data.postList[].imageUrl").type(JsonFieldType.STRING).description("피드 대표 사진")
        );
    }

    private Snippet lookupMyPageResponseBody() {
        return responseFields(
                fieldWithPath("code").type(JsonFieldType.STRING).description(Constants.RESPONSE_ID),
                fieldWithPath("message").type(JsonFieldType.STRING).description(Constants.RESPONSE_MESSAGE),
                fieldWithPath("data").type(JsonFieldType.OBJECT).description(Constants.RESPONSE_DATA).optional(),
                fieldWithPath("data.userId").type(JsonFieldType.STRING).description("사용자 ID"),
                fieldWithPath("data.email").type(JsonFieldType.STRING).description("사용자 Email"),
                fieldWithPath("data.password").type(JsonFieldType.STRING).description("사용자 Password"),
                fieldWithPath("data.phone").type(JsonFieldType.STRING).description("사용자 phone"),
                fieldWithPath("data.name").type(JsonFieldType.STRING).description("사용자 이름"),
                fieldWithPath("data.nickName").type(JsonFieldType.STRING).description("사용자 이름"),
                fieldWithPath("data.profileImage").type(JsonFieldType.STRING).description("사용자 프로필 이미지 주소")
        );
    }
}
