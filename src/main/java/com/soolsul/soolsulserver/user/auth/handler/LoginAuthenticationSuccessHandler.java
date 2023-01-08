package com.soolsul.soolsulserver.user.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soolsul.soolsulserver.common.response.BaseResponse;
import com.soolsul.soolsulserver.user.auth.domain.CustomUser;
import com.soolsul.soolsulserver.user.auth.jwt.JwtToken;
import com.soolsul.soolsulserver.user.auth.jwt.JwtTokenFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static com.soolsul.soolsulserver.common.response.ResponseCodes.USER_LOGIN_SUCCESS;

public class LoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtTokenFactory tokenProvider;

    @Autowired
    private MessageSource messageSource;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomUser user = (CustomUser) authentication.getPrincipal();

        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        List<GrantedAuthority> collect = user.getAuthorities()
                .stream()
                .map(userRole -> userRole.getAuthority())
                .collect(Collectors.toSet())
                .stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        JwtToken jwtToken = JwtToken.builder()
                .accessToken(tokenProvider.createAccessToken(user.getId(), collect))
                .refreshToken(tokenProvider.createRefreshToken(user.getId()))
                .build();

        String message = messageSource.getMessage(USER_LOGIN_SUCCESS.getCode(), new String[]{}, Locale.getDefault());

        BaseResponse<JwtToken> tokenResponse = new BaseResponse<>(USER_LOGIN_SUCCESS.getCode(), message, jwtToken);

        objectMapper.writeValue(response.getWriter(), tokenResponse);
    }
}
