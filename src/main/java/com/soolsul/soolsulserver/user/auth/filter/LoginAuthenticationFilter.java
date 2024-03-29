package com.soolsul.soolsulserver.user.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soolsul.soolsulserver.user.auth.filter.dto.UserDto;
import com.soolsul.soolsulserver.user.auth.token.LoginAuthenticationToken;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.ObjectUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private ObjectMapper objectMapper;

    public LoginAuthenticationFilter() {
        super(new AntPathRequestMatcher("/api/auth/login"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (!isContentTypeJson(request)) {
            throw new IllegalStateException("Authentication is not supportes");
        }

        UserDto userDto = objectMapper.readValue(request.getReader(), UserDto.class);
        if (isNoUserInformation(userDto)) {
            throw new IllegalArgumentException("UserId is empty");
        }

        LoginAuthenticationToken authenticationToken = new LoginAuthenticationToken(userDto.getEmail(), userDto.getPassword());

        return getAuthenticationManager().authenticate(authenticationToken);
    }

    private boolean isNoUserInformation(UserDto userDto) {
        return ObjectUtils.isEmpty(userDto.getEmail()) || ObjectUtils.isEmpty(userDto.getPassword());
    }

    private boolean isContentTypeJson(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.CONTENT_TYPE);
        return (header != null) && header.contains(MediaType.APPLICATION_JSON_VALUE);
    }

}
