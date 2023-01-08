package com.soolsul.soolsulserver.user.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soolsul.soolsulserver.common.response.BaseResponse;
import com.soolsul.soolsulserver.common.response.ResponseCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class JwtLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MessageSource messageSource;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        String message = messageSource.getMessage(ResponseCodes.USER_LOGOUT_SUCCESS.getCode(), new String[]{}, Locale.getDefault());

        BaseResponse<Void> logoutResponse = new BaseResponse<>(ResponseCodes.USER_LOGOUT_SUCCESS.getCode(), message, null);

        objectMapper.writeValue(response.getWriter(), logoutResponse);
    }
}
