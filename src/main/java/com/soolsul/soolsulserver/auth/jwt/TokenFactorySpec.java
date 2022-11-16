package com.soolsul.soolsulserver.auth.jwt;

import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

public interface TokenFactorySpec {

    JwtToken issue(String userId, List<GrantedAuthority> roles);

    JwtToken getTokenFromHeader(HttpServletRequest request);

    String getUserIdFromToken(String accessToken);

    Collection<GrantedAuthority> getRolesFromToken(String accessToken);

    boolean isValidRefreshToken(String refreshToken);

    boolean isValidAccessToken(String accessToken);

    String createAccessToken(String userId, List<GrantedAuthority> roles);

    String createRefreshToken();
}
