package com.soolsul.soolsulserver.auth.jwt;

import com.soolsul.soolsulserver.auth.Authority;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider implements TokenProviderSpec {

    private final Key ACCESS_PRIVATE_KEY;
    private final Key REFRESH_PRIVATE_KEY;

    @Value("${jwt.expire-length}")
    private Long accessExpirationMillis;

    @Value("${jwt.refresh-length}")
    private Long refreshExpirationMillis;

    public JwtTokenProvider(
            @Value("${jwt.access.private}") String accessPrivateKey,
            @Value("${jwt.refresh.private}") String refreshPrivateKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        ACCESS_PRIVATE_KEY = getPrivateKey(accessPrivateKey);
        REFRESH_PRIVATE_KEY = getPrivateKey(refreshPrivateKey);
    }

    @Override
    public JwtToken issue(String userId, List<GrantedAuthority> roles) {
        return JwtToken.builder()
                .accessToken(createAccessToken(userId, roles))
                .refreshToken(createRefreshToken())
                .build();
    }

    @Override
    public JwtToken getTokenFromHeader(HttpServletRequest request) {
        return JwtToken.builder()
                .accessToken(request.getHeader("authorization"))
                .refreshToken(request.getHeader("x-refresh-token"))
                .build();
    }

    @Override
    public String getUserIdFromToken(String accessToken) {
        Object userId = Jwts.parserBuilder()
                .setSigningKey(ACCESS_PRIVATE_KEY)
                .build()
                .parseClaimsJws(accessToken).getBody().get("userId");

        return (String) userId;
    }

    @Override
    public List<Authority> getRolesFromToken(String accessToken) {
        Object roles = Jwts.parserBuilder()
                .setSigningKey(ACCESS_PRIVATE_KEY)
                .build()
                .parseClaimsJws(accessToken).getBody().get("roles");

        return (List<Authority>) roles;
    }

    @Override
    public boolean isValidRefreshToken(String refreshToken) {
        if (StringUtils.hasText(refreshToken)) {
            try {
                Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(REFRESH_PRIVATE_KEY).build()
                        .parseClaimsJws(refreshToken);

                return !claims.getBody().getExpiration().before(new Date());
            } catch (ExpiredJwtException e) {
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean isValidAccessToken(String accessToken) {
        if (StringUtils.hasText(accessToken)) {
            try {
                Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(ACCESS_PRIVATE_KEY).build()
                        .parseClaimsJws(accessToken);

                return !claims.getBody().getExpiration().before(new Date());
            } catch (ExpiredJwtException e) {
                return false;
            }
        }
        return false;
    }

    @Override
    public String createAccessToken(String userId, List<GrantedAuthority> roles) {
        Date createdDate = new Date();
        Date expirationDate = new Date(createdDate.getTime() + accessExpirationMillis);

        return Jwts.builder()
                .setSubject(userId)
                .claim("userId", userId)
                .claim("roles", roles)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(ACCESS_PRIVATE_KEY)
                .compact();
    }

    @Override
    public String createRefreshToken() {
        Date createdDate = new Date();
        Date expirationDate = new Date(createdDate.getTime() + refreshExpirationMillis);

        return Jwts.builder()
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(REFRESH_PRIVATE_KEY)
                .compact();
    }

    private Key getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return Keys.hmacShaKeyFor(privateKey.getBytes(StandardCharsets.UTF_8));
    }
}
