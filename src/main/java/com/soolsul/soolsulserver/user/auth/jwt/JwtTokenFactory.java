package com.soolsul.soolsulserver.user.auth.jwt;

import com.soolsul.soolsulserver.user.auth.redis.RedisCachingService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtTokenFactory implements TokenFactorySpec {

    private final RedisCachingService redisCachingService;
    private final Key accessPrivateKey;
    private final Key refreshPrivateKey;

    @Value("${jwt.expire-length}")
    private Long accessExpirationMillis;

    @Value("${jwt.refresh-length}")
    private Long refreshExpirationMillis;

    public JwtTokenFactory(
            @Value("${jwt.access.private}") String accessPrivateKey,
            @Value("${jwt.refresh.private}") String refreshPrivateKey,
            RedisCachingService redisCachingService)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.accessPrivateKey = getPrivateKey(accessPrivateKey);
        this.refreshPrivateKey = getPrivateKey(refreshPrivateKey);
        this.redisCachingService = redisCachingService;
    }

    @Override
    public JwtToken issue(String userId, List<GrantedAuthority> roles) {
        return JwtToken.builder()
                .accessToken(createAccessToken(userId, roles))
                .refreshToken(createRefreshToken(userId))
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
        return (String) Jwts.parserBuilder()
                .setSigningKey(accessPrivateKey)
                .build()
                .parseClaimsJws(accessToken).getBody().get("userId");
    }

    // 출처 : https://velog.io/@tlatldms/서버개발캠프-Spring-security-refreshing-JWT-DB접근없이-인증과-파싱하기
    @Override
    public Collection<GrantedAuthority> getRolesFromToken(String accessToken) {
        List<String> roles = (List) Jwts.parserBuilder()
                .setSigningKey(accessPrivateKey)
                .build()
                .parseClaimsJws(accessToken).getBody().get("roles");

        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public Map<String, Object> getUserParseInfo(String accessToken) {
        Claims parseInfo = Jwts.parserBuilder()
                .setSigningKey(accessPrivateKey)
                .build()
                .parseClaimsJws(accessToken).getBody();

        Map<String, Object> result = new HashMap<>();
        result.put("userId", parseInfo.get("userId"));
        result.put("roles", parseInfo.get("roles", List.class));
        return result;
    }

    @Override
    public boolean isValidRefreshToken(String refreshToken) {
        if (StringUtils.hasText(refreshToken)) {
            try {
                Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(refreshPrivateKey).build()
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
                Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(accessPrivateKey).build()
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

        Map<String, Object> claims = new HashMap<>();
        List<String> roleList = roles.stream()
                .map(role -> role.getAuthority())
                .collect(Collectors.toList());

        claims.put("roles", roleList);
        claims.put("userId", userId);

        return Jwts.builder()
                .setSubject(userId)
                .setClaims(claims)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(accessPrivateKey)
                .compact();
    }

    @Override
    public String createRefreshToken(String userId) {
        Date createdDate = new Date();
        Date expirationDate = new Date(createdDate.getTime() + refreshExpirationMillis);

        String refreshToken = Jwts.builder()
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(refreshPrivateKey)
                .compact();

        redisCachingService.setValuesWithDuration(userId, refreshToken, Duration.ofMillis(refreshExpirationMillis));

        return refreshToken;
    }

    private Key getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return Keys.hmacShaKeyFor(privateKey.getBytes(StandardCharsets.UTF_8));
    }
}
