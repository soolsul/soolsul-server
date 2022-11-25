package com.soolsul.soolsulserver.user.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soolsul.soolsulserver.user.auth.filter.dto.UserDto;
import com.soolsul.soolsulserver.user.auth.token.FirstLoginAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.ObjectUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FirstLoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public FirstLoginAuthenticationFilter() {
        super(new AntPathRequestMatcher("/api/auth/login"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (!isJsonLogin(request)) {
            throw new IllegalStateException("Authentication is not supportes");
        }

        UserDto userDto = objectMapper.readValue(request.getReader(), UserDto.class);
        if (isNoUserInformation(userDto)) {
            throw new IllegalArgumentException("UserId is empty");
        }

        FirstLoginAuthenticationToken authenticationToken = new FirstLoginAuthenticationToken(userDto.getEmail(), userDto.getPassword());

        return getAuthenticationManager().authenticate(authenticationToken);
    }

    private boolean isNoUserInformation(UserDto userDto) {
        return ObjectUtils.isEmpty(userDto.getEmail()) || ObjectUtils.isEmpty(userDto.getPassword());
    }

    private boolean isJsonLogin(HttpServletRequest request) {
        if ("JSONLoginHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            return true;
        }

        return false;
    }
}
