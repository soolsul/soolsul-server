package com.soolsul.soolsulserver.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soolsul.soolsulserver.common.response.BaseResponse;
import com.soolsul.soolsulserver.common.response.ResponseCodeAndMessages;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtLogoutSuccessHandler implements LogoutSuccessHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        BaseResponse<Void> logoutResponse = new BaseResponse<>(ResponseCodeAndMessages.USER_LOGOUT_SUCCESS, null);

        objectMapper.writeValue(response.getWriter(), logoutResponse);
    }
}
