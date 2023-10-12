package com.soolsul.soolsulserver.documentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.contract.spec.internal.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

import com.soolsul.soolsulserver.common.annotation.MockCustomUser;
import com.soolsul.soolsulserver.post.common.dto.response.ScrapedPostLookUpResponse;
import com.soolsul.soolsulserver.post.common.dto.response.UserPostLookUpResponse;
import com.soolsul.soolsulserver.post.common.dto.response.UserReplyLookUpResponse;
import com.soolsul.soolsulserver.user.auth.persistence.dto.response.UserEditFormResponse;
import com.soolsul.soolsulserver.user.auth.persistence.dto.response.UserLookUpResponse;
import com.soolsul.soolsulserver.user.mypage.common.dto.reqeust.UserInfoEditRequest;
import com.soolsul.soolsulserver.user.mypage.common.dto.response.ScrapedPostListLookUpResponse;
import com.soolsul.soolsulserver.user.mypage.common.dto.response.UserPostListLookUpResponse;
import com.soolsul.soolsulserver.user.mypage.common.dto.response.UserReplyListLookUpResponse;
import com.soolsul.soolsulserver.user.mypage.facade.MyPageCommandFacade;
import com.soolsul.soolsulserver.user.mypage.facade.MyPageQueryFacade;

class MyPageDocumentation extends Documentation {

    @Autowired
    private MyPageQueryFacade myPageQueryFacade;

    @Autowired
    private MyPageCommandFacade myPageCommandFacade;

    @DisplayName("문서화 : 유저 정보 상세조회")
    @MockCustomUser
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

    @DisplayName("문서화 : 다른 유저 정보 상세조회")
    @Test
    public void find_other_user_detail_info() throws Exception {
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

        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/mypages/{userId}", "ff808081854e558801854e559bbe0003")
                        .header("Authorization", "bearer login-jwt-token")
                        .accept(MediaTypes.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(
                        document("search-detail-info-other-mypage",
                                lookupMyPageResponseBody())
                );
    }

    @DisplayName("문서화 : 사용자 작성 리뷰 전체 조회")
    @MockCustomUser
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
    @MockCustomUser
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
    @MockCustomUser
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

    @DisplayName("문서화 : 사용자 정보 수정 폼 요청")
    @MockCustomUser
    @Test
    public void request_modify_user_form() throws Exception {
        UserEditFormResponse userEditFormResponse = new UserEditFormResponse("origin_image_url", "origin_nick_name", "origin@test.com");

        given(myPageQueryFacade.findUserEditForm(any())).willReturn(userEditFormResponse);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/mypages/edit")
                        .header("Authorization", "bearer login-jwt-token")
                        .accept(MediaTypes.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(
                        document("modify-user-form-mypage",
                                lookupModifyFormResponseBody())
                );
    }

    @DisplayName("문서화 : 사용자 정보 수정 요청")
    @MockCustomUser
    @Test
    public void send_modified_user_data() throws Exception {
        UserInfoEditRequest editRequest = new UserInfoEditRequest("edit_image_url", "edit_nick_name", "change@test.com");

        doNothing().when(myPageCommandFacade).editUserInfo(any(), any());

        mockMvc.perform(RestDocumentationRequestBuilders.patch("/api/mypages/edit")
                        .header("Authorization", "bearer login-jwt-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaTypes.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(editRequest))
                )
                .andExpect(status().isOk())
                .andDo(
                        document("modify-user-mypage",
                                userModifyRequestBody(),
                                userModifyResponseBody())
                );
    }

    private Snippet userModifyRequestBody() {
        return requestFields(
                fieldWithPath("imageUrl").type(JsonFieldType.STRING).description("프로필 사진"),
                fieldWithPath("nickName").type(JsonFieldType.STRING).description("사용자 별칭"),
                fieldWithPath("email").type(JsonFieldType.STRING).description("사용자 이메일")
        );
    }

    private Snippet userModifyResponseBody() {
        return responseFields(
                fieldWithPath("code").description(Constants.RESPONSE_ID),
                fieldWithPath("message").description(Constants.RESPONSE_MESSAGE),
                fieldWithPath("data").description(Constants.RESPONSE_DATA).optional());
    }

    private Snippet lookupModifyFormResponseBody() {
        return responseFields(
                fieldWithPath("code").type(JsonFieldType.STRING).description(Constants.RESPONSE_ID),
                fieldWithPath("message").type(JsonFieldType.STRING).description(Constants.RESPONSE_MESSAGE),
                fieldWithPath("data").type(JsonFieldType.OBJECT).description(Constants.RESPONSE_DATA).optional(),
                fieldWithPath("data.imageUrl").type(JsonFieldType.STRING).description("프로필 사진"),
                fieldWithPath("data.nickName").type(JsonFieldType.STRING).description("사용자 별칭"),
                fieldWithPath("data.email").type(JsonFieldType.STRING).description("사용자 이메일")
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
