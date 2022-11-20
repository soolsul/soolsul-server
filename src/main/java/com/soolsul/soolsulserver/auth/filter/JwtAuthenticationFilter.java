package com.soolsul.soolsulserver.auth.filter;

import com.soolsul.soolsulserver.auth.CustomUser;
import com.soolsul.soolsulserver.auth.business.CustomUserDetailsService;
import com.soolsul.soolsulserver.auth.exception.UserUnauthorizedException;
import com.soolsul.soolsulserver.auth.jwt.JwtTokenFactory;
import com.soolsul.soolsulserver.auth.redis.RedisService;
import com.soolsul.soolsulserver.auth.util.AuthorizationExtractor;
import com.soolsul.soolsulserver.auth.util.AuthorizationType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenFactory jwtTokenFactory;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private RedisService redisService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper wrappingRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappingResponse = new ContentCachingResponseWrapper(response);

        String accessToken = convert(request);
        log.info("[AccessToken] : {}", accessToken);

        if (isAlreadyLogout(accessToken)) {
            throw new UserUnauthorizedException();
        }

        if (isInvalidAccessToken(accessToken)) {
            filterChain.doFilter(wrappingRequest, wrappingResponse);
            wrappingResponse.copyBodyToResponse();
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(buildAuthentication(accessToken));
        log.info("[Authentication 등록 완료]");

        filterChain.doFilter(wrappingRequest, wrappingResponse);
        wrappingResponse.copyBodyToResponse();
    }

    private boolean isInvalidAccessToken(String accessToken) {
        return !StringUtils.hasText(accessToken) || !jwtTokenFactory.isValidAccessToken(accessToken);
    }

    private boolean isAlreadyLogout(String accessToken) {
        return StringUtils.hasText(accessToken) && redisService.getValues(accessToken) != null;
    }

    private String convert(HttpServletRequest request) {
        return AuthorizationExtractor.extract(request, AuthorizationType.BEARER);
    }

    private UsernamePasswordAuthenticationToken buildAuthentication(String accessToken) {
        String userIdFromToken = jwtTokenFactory.getUserIdFromToken(accessToken);
        Collection<GrantedAuthority> rolesFromToken = jwtTokenFactory.getRolesFromToken(accessToken);
        CustomUser findUser = userDetailsService.findUserForAuthentication(userIdFromToken);
        log.info("[User Info] : {}", findUser.getEmail());
        return new UsernamePasswordAuthenticationToken(findUser, "", rolesFromToken);
    }
}
