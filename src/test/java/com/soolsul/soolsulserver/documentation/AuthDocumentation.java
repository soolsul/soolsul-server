package com.soolsul.soolsulserver.documentation;

import com.soolsul.soolsulserver.user.auth.business.CustomUserDetailsService;
import com.soolsul.soolsulserver.user.auth.presentation.AuthController;
import com.soolsul.soolsulserver.user.auth.presentation.dto.UserRegisterRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.spec.internal.MediaTypes;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthController.class,
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfigurer.class)},
        excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class AuthDocumentation extends Documentation {

    @MockBean
    CustomUserDetailsService userDetailsService;

    @DisplayName("문서화 : 사용자 회원 가입")
    @Test
    public void user_register() throws Exception {
        UserRegisterRequest registerRequest = new UserRegisterRequest("test@email.com", "password", "010-1234-5678", "test_user", "test_nickname");

        doNothing().when(userDetailsService).register(any());

        mockMvc.perform(post("/api/auth/register")
                        .header("Authorization", "bearer login-jwt-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaTypes.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest))
                )
                .andExpect(status().isCreated())
                .andDo(
                        document("user-register-auth",
                                userRegisterRequestBody(),
                                userRegisterResponseBody())
                );
    }

    @DisplayName("문서화 : 사용자 회원 탈퇴")
    @Test
    public void user_delete() throws Exception {

        doNothing().when(userDetailsService).delete(any());

        mockMvc.perform(delete("/api/auth")
                        .header("Authorization", "bearer login-jwt-token")
                        .accept(MediaTypes.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(
                        document("user-delete-auth",
                                userDeleteResponseBody())
                );
    }

    private Snippet userDeleteResponseBody() {
        return responseFields(
                fieldWithPath("code").description(Constants.RESPONSE_ID),
                fieldWithPath("message").description(Constants.RESPONSE_MESSAGE),
                fieldWithPath("data").description(Constants.RESPONSE_DATA).optional());
    }

    private Snippet userRegisterRequestBody() {
        return requestFields(
                fieldWithPath("email").type(JsonFieldType.STRING).description("사용자 이메일"),
                fieldWithPath("password").type(JsonFieldType.STRING).description("사용자 비밀번호"),
                fieldWithPath("phone").type(JsonFieldType.STRING).description("사용자 전화번호"),
                fieldWithPath("name").type(JsonFieldType.STRING).description("사용자 이름"),
                fieldWithPath("nickname").type(JsonFieldType.STRING).description("사용자 별칭")
        );
    }

    private Snippet userRegisterResponseBody() {
        return responseFields(
                fieldWithPath("code").description(Constants.RESPONSE_ID),
                fieldWithPath("message").description(Constants.RESPONSE_MESSAGE),
                fieldWithPath("data").description(Constants.RESPONSE_DATA).optional());
    }
}
