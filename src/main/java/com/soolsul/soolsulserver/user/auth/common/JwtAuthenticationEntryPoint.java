package com.soolsul.soolsulserver.user.auth.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soolsul.soolsulserver.common.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import static com.soolsul.soolsulserver.common.response.ResponseCodes.USER_UNAUTHORIZED;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MessageSource messageSource;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {

        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        String message = messageSource.getMessage(USER_UNAUTHORIZED.getCode(), new String[]{}, Locale.getDefault());

        BaseResponse<Void> baseResponse = new BaseResponse<>(USER_UNAUTHORIZED.getCode(), message, null);
        objectMapper.writeValue(response.getWriter(), baseResponse);
    }
}
