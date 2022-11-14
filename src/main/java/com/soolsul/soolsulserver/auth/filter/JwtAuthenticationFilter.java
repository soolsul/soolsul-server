package com.soolsul.soolsulserver.auth.filter;

import com.soolsul.soolsulserver.auth.Authority;
import com.soolsul.soolsulserver.auth.jwt.JwtToken;
import com.soolsul.soolsulserver.auth.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter() {
        super(new AntPathRequestMatcher("/api/login"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        JwtToken token = jwtTokenProvider.getTokenFromHeader(request);
        if (token.getAccessToken() == null) {
            throw new IllegalStateException("Authentication is not supported");
        }

        String accessToken = token.getAccessToken();
        String userId = String.valueOf(jwtTokenProvider.getUserIdFromToken(accessToken));
        List<Authority> roles = jwtTokenProvider.getRolesFromToken(accessToken);

        if (StringUtils.isEmpty(userId)) {
            throw new IllegalArgumentException("UserId is empty");
        }

        //JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(userId);

        return getAuthenticationManager().authenticate(null);
    }
}
