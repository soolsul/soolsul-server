package com.soolsul.soolsulserver.documentation;

import com.soolsul.soolsulserver.reply.common.dto.request.PostReplyRequest;
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
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;

import static org.mockito.ArgumentMatchers.any;
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

@WebMvcTest(controllers = ReplyController.class,
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfigurer.class)},
        excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class ReplyDocumentation extends Documentation {

    @MockBean
    ReplyFacadeGateway replyFacadeGateway;

    @DisplayName("문서화 : Reply 생성")
    @Test
    public void create_reply_success() throws Exception {
        PostReplyRequest replyRequest = new PostReplyRequest("this is reply contents");

        doNothing().when(replyFacadeGateway).create(any(), any(), any());

        mockMvc.perform(post("/api/posts/{postId}/replies", "post_uuid")
                        .header("Authorization", "bearer login-jwt-token")
                        .contentType(MediaTypes.APPLICATION_JSON)
                        .accept(MediaTypes.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(replyRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("create-reply",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                createReplyRequestBody(),
                                noContentsReplyResponseBody())
                );
    }

    private Snippet createReplyRequestBody() {
        return requestFields(fieldWithPath("contents").type(JsonFieldType.STRING).description("댓글 내용"));
    }

    private Snippet noContentsReplyResponseBody() {
        return responseFields(
                fieldWithPath("code").description(Constants.RESPONSE_ID),
                fieldWithPath("message").description(Constants.RESPONSE_MESSAGE),
                fieldWithPath("data").description(Constants.RESPONSE_DATA).optional());
    }
}
