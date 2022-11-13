package com.soolsul.soolsulserver.common.jwt;

import com.soolsul.soolsulserver.domain.auth.Authority;
import com.soolsul.soolsulserver.domain.auth.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

public interface TokenProvider {

    JwtToken issue(Long userId, List<Authority> roles);

    JwtToken getTokenFromHeader(HttpServletRequest request);
    Long getUserIdFromToken(String accessToken);

    List<Authority> getRolesFromToken(String accessToken);

    void validateRefreshToken(String refreshToken);
    Jws<Claims> verifyAccessToken(String accessToken);

    String createAccessToken(Long userId, List<Authority> roles);
    String createRefreshToken();
}
