package com.soolsul.soolsulserver.documentation;

import com.soolsul.soolsulserver.common.annotation.MockCustomUser;
import com.soolsul.soolsulserver.reply.common.dto.request.ReplyCreateRequest;
import com.soolsul.soolsulserver.reply.common.dto.request.ReplyModifyRequest;
import com.soolsul.soolsulserver.reply.common.dto.response.PostRepliesResponse;
import com.soolsul.soolsulserver.reply.common.dto.response.ReplyDetailResponse;
import com.soolsul.soolsulserver.reply.facade.ReplyFacadeGateway;
import com.soolsul.soolsulserver.reply.presentation.ReplyController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.spec.internal.MediaTypes;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ReplyController.class,
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfigurer.class)},
        excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class ReplyDocumentation extends Documentation {

    @MockBean
    ReplyFacadeGateway replyFacadeGateway;

    @DisplayName("????????? : Reply ??????")
    @MockCustomUser
    @Test
    void create_reply_success() throws Exception {
        ReplyCreateRequest replyRequest = new ReplyCreateRequest("this is reply contents");

        doNothing().when(replyFacadeGateway).create(any(), any(), any());

        mockMvc.perform(RestDocumentationRequestBuilders.post("/api/posts/{postId}/replies", "post_uuid")
                        .header("Authorization", "bearer login-jwt-token")
                        .contentType(MediaTypes.APPLICATION_JSON)
                        .accept(MediaTypes.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(replyRequest)))
                .andExpect(status().isOk())
                .andDo(
                        document("create-reply",
                                createReplyRequestPath(),
                                createReplyRequestBody(),
                                noContentsReplyResponseBody())
                );
    }

    @DisplayName("????????? : Reply ?????? ??????")
    @Test
    void find_reply_list_success() throws Exception {
        ReplyDetailResponse replyOne = new ReplyDetailResponse("reply_1", "contents_1", "user_1", "nickname_1", "url_1");
        ReplyDetailResponse replyTwo = new ReplyDetailResponse("reply_2", "contents_2", "user_2", "nickname_2", "url_2");
        SliceImpl slice = new SliceImpl(List.of(replyOne, replyTwo), PageRequest.of(0, 10), false);

        PostRepliesResponse postRepliesResponse = new PostRepliesResponse(slice);

        given(replyFacadeGateway.findReplies(any(), any())).willReturn(postRepliesResponse);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/posts/{postId}/replies", "post_uuid")
                        .accept(MediaTypes.APPLICATION_JSON)
                        .header("Authorization", "bearer login-jwt-token"))
                .andExpect(status().isOk())
                .andDo(
                        document("find-all-reply",
                                findAllReplyRequestPath(),
                                findAllReplyResponseBody())
                );
    }

    @DisplayName("????????? : Reply ??????")
    @MockCustomUser
    @Test
    void modify_reply_success() throws Exception {
        ReplyModifyRequest modifyRequest = new ReplyModifyRequest("modify contents");

        doNothing().when(replyFacadeGateway).modify(any(), any(), any(), any());

        mockMvc.perform(RestDocumentationRequestBuilders.put("/api/posts/{postId}/replies/{replyId}", "post_uuid", "reply_uuid")
                        .header("Authorization", "bearer login-jwt-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaTypes.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modifyRequest)))
                .andExpect(status().isOk())
                .andDo(
                        document("modify-reply",
                                modifyReplyRequestPath(),
                                noContentsReplyResponseBody())
                );
    }

    @DisplayName("????????? : Reply ??????")
    @MockCustomUser
    @Test
    void delete_reply_success() throws Exception {

        doNothing().when(replyFacadeGateway).create(any(), any(), any());

        mockMvc.perform(RestDocumentationRequestBuilders.delete("/api/posts/{postId}/replies/{replyId}", "post_uuid", "reply_uuid")
                        .header("Authorization", "bearer login-jwt-token")
                        .accept(MediaTypes.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(
                        document("delete-reply",
                                deleteReplyRequestPath(),
                                noContentsReplyResponseBody())
                );
    }

    private Snippet deleteReplyRequestPath() {
        return pathParameters(
                parameterWithName("postId").description("?????? ID"),
                parameterWithName("replyId").description("?????? ID")
        );
    }

    private Snippet findAllReplyRequestPath() {
        return pathParameters(
                parameterWithName("postId").description("?????? ID")
        );
    }

    private Snippet findAllReplyResponseBody() {
        return responseFields(
                fieldWithPath("code").type(JsonFieldType.STRING).description(Constants.RESPONSE_ID),
                fieldWithPath("message").type(JsonFieldType.STRING).description(Constants.RESPONSE_MESSAGE),
                fieldWithPath("data").type(JsonFieldType.OBJECT).description(Constants.RESPONSE_DATA).optional(),
                fieldWithPath("data.replies.content[].replyId").type(JsonFieldType.STRING).description("?????? ID"),
                fieldWithPath("data.replies.content[].contents").type(JsonFieldType.STRING).description("????????? ?????? ??????"),
                fieldWithPath("data.replies.content[].userId").type(JsonFieldType.STRING).description("?????? ????????? ID"),
                fieldWithPath("data.replies.content[].nickName").type(JsonFieldType.STRING).description("?????? ????????? ??????"),
                fieldWithPath("data.replies.content[].profileUrl").type(JsonFieldType.STRING).description("?????? ????????? ????????? url"),
                fieldWithPath("data.replies.pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("?????? ??????"),
                fieldWithPath("data.replies.pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("?????? ????????????"),
                fieldWithPath("data.replies.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("???????????? ????????????"),
                fieldWithPath("data.replies.pageable.offset").type(JsonFieldType.NUMBER).description("?????? ???????????? ??? ?????? ????????? ???"),
                fieldWithPath("data.replies.pageable.pageNumber").type(JsonFieldType.NUMBER).description("????????? ?????? (0??? ?????? ??????)"),
                fieldWithPath("data.replies.pageable.pageSize").type(JsonFieldType.NUMBER).description("??? ??????????????? ???????????? ????????? ???"),
                fieldWithPath("data.replies.pageable.paged").type(JsonFieldType.BOOLEAN).description("??????????????? ???"),
                fieldWithPath("data.replies.pageable.unpaged").type(JsonFieldType.BOOLEAN).description("???????????? ?????? ??????"),
                fieldWithPath("data.replies.first").type(JsonFieldType.BOOLEAN).description("??? ????????? ???????"),
                fieldWithPath("data.replies.last").type(JsonFieldType.BOOLEAN).description("????????? ????????? ???????"),
                fieldWithPath("data.replies.size").type(JsonFieldType.NUMBER).description("?????????"),
                fieldWithPath("data.replies.number").type(JsonFieldType.NUMBER).description("??????"),
                fieldWithPath("data.replies.sort.empty").type(JsonFieldType.BOOLEAN).description("?????? ??????"),
                fieldWithPath("data.replies.sort.sorted").type(JsonFieldType.BOOLEAN).description("?????? ????????????"),
                fieldWithPath("data.replies.sort.unsorted").type(JsonFieldType.BOOLEAN).description("???????????? ????????????"),
                fieldWithPath("data.replies.numberOfElements").type(JsonFieldType.NUMBER).description("????????? ???"),
                fieldWithPath("data.replies.empty").type(JsonFieldType.BOOLEAN).description("???????????????")
        );
    }

    private Snippet modifyReplyRequestPath() {
        return requestFields(fieldWithPath("contents").type(JsonFieldType.STRING).description("????????? ?????? ??????"));
    }

    private Snippet createReplyRequestPath() {
        return pathParameters(
                parameterWithName("postId").description("?????? ID")
        );
    }

    private Snippet createReplyRequestBody() {
        return requestFields(fieldWithPath("contents").type(JsonFieldType.STRING).description("?????? ??????"));
    }

    private Snippet noContentsReplyResponseBody() {
        return responseFields(
                fieldWithPath("code").description(Constants.RESPONSE_ID),
                fieldWithPath("message").description(Constants.RESPONSE_MESSAGE),
                fieldWithPath("data").description(Constants.RESPONSE_DATA).optional());
    }
}
