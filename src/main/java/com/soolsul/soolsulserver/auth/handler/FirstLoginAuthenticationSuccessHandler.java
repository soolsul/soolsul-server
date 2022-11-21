package com.soolsul.soolsulserver.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soolsul.soolsulserver.auth.CustomUser;
import com.soolsul.soolsulserver.auth.jwt.JwtToken;
import com.soolsul.soolsulserver.auth.jwt.JwtTokenFactory;
import com.soolsul.soolsulserver.common.response.BaseResponse;
import com.soolsul.soolsulserver.common.response.ResponseCodeAndMessages;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.stream.Collectors;

public class FirstLoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private JwtTokenFactory tokenProvider;

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

        BaseResponse<JwtToken> tokenResponse = new BaseResponse<>(ResponseCodeAndMessages.USER_LOGIN_SUCCESS, jwtToken);

        objectMapper.writeValue(response.getWriter(), tokenResponse);
    }
}
