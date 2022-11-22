package com.soolsul.soolsulserver.auth.handler;

import com.soolsul.soolsulserver.auth.jwt.JwtTokenFactory;
import com.soolsul.soolsulserver.auth.redis.RedisCachingService;
import com.soolsul.soolsulserver.auth.util.AuthorizationExtractor;
import com.soolsul.soolsulserver.auth.util.AuthorizationType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class JwtLogoutHandler implements LogoutHandler {

    @Value("${jwt.expire-length}")
    private Long accessExpirationMillis;

    @Autowired
    private JwtTokenFactory jwtTokenFactory;

    @Autowired
    private RedisCachingService redisCachingService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String accessToken = convert(request);
        log.info("[AccessToken] : {}", accessToken);

        if (isInvalidAccessToken(accessToken)) {
            return;
        }

        String userIdFromToken = jwtTokenFactory.getUserIdFromToken(accessToken);
        if (StringUtils.hasText(userIdFromToken) && isHasRefreshToken(userIdFromToken)) {
            // delete refresh token
            redisCachingService.deleteValues(userIdFromToken);

            // accessToken add black list
            redisCachingService.addBlackList(accessToken, accessExpirationMillis);

            SecurityContextHolder.clearContext();
        }
    }

    private boolean isHasRefreshToken(String userIdFromToken) {
        return StringUtils.hasText(redisCachingService.getValues(userIdFromToken));
    }

    private boolean isInvalidAccessToken(String accessToken) {
        return !StringUtils.hasText(accessToken) || !jwtTokenFactory.isValidAccessToken(accessToken);
    }

    private String convert(HttpServletRequest request) {
        return AuthorizationExtractor.extract(request, AuthorizationType.BEARER);
    }
}
