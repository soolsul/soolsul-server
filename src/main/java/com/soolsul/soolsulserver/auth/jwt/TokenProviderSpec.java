package com.soolsul.soolsulserver.auth.jwt;

import com.soolsul.soolsulserver.auth.Authority;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface TokenProviderSpec {

    JwtToken issue(String userId, List<GrantedAuthority> roles);

    JwtToken getTokenFromHeader(HttpServletRequest request);

    Long getUserIdFromToken(String accessToken);

    List<Authority> getRolesFromToken(String accessToken);

    void validateRefreshToken(String refreshToken);

    Jws<Claims> verifyAccessToken(String accessToken);

    String createAccessToken(String userId, List<GrantedAuthority> roles);

    String createRefreshToken();
}
