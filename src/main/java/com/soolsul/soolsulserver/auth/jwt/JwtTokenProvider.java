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

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider implements TokenProviderSpec {

    private final Key ACCESS_PUBLIC_KEY;
    private final Key REFRESH_PUBLIC_KEY;
    private final Key ACCESS_PRIVATE_KEY;
    private final Key REFRESH_PRIVATE_KEY;

    @Value("${jwt.expire-length}")
    private Long accessExpirationMillis;

    @Value("${jwt.refresh-length}")
    private Long refreshExpirationMillis;

    public JwtTokenProvider(
            @Value("${jwt.access.private}") String accessPrivateKey,
            @Value("${jwt.refresh.private}") String refreshPrivateKey,
            @Value("${jwt.access.public}") String accessPublicKey,
            @Value("${jwt.refresh.public}") String refreshPublicKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        ACCESS_PRIVATE_KEY = getPrivateKey(accessPrivateKey);
        ACCESS_PUBLIC_KEY = getPublicKey(accessPublicKey);
        REFRESH_PRIVATE_KEY = getPrivateKey(refreshPrivateKey);
        REFRESH_PUBLIC_KEY = getPublicKey(refreshPublicKey);
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
                .accessToken(request.getHeader("x-access-token"))
                .refreshToken(request.getHeader("x-refresh-token"))
                .build();
    }

    @Override
    public Long getUserIdFromToken(String accessToken) {
        Object userId = Jwts.parserBuilder()
                .setSigningKey(ACCESS_PUBLIC_KEY)
                .build()
                .parseClaimsJws(accessToken).getBody().get("userId");
        return (Long) userId;
    }

    @Override
    public List<Authority> getRolesFromToken(String accessToken) {
        Object roles = Jwts.parserBuilder()
                .setSigningKey(ACCESS_PUBLIC_KEY)
                .build()
                .parseClaimsJws(accessToken).getBody().get("roles");
        return (List<Authority>) roles;
    }

    @Override
    public void validateRefreshToken(String refreshToken) {
        if (!refreshToken.isEmpty()) {
            try {
                Jwts.parserBuilder().setSigningKey(REFRESH_PUBLIC_KEY).build()
                        .parseClaimsJws(refreshToken);
            } catch (ExpiredJwtException e) {//헤더, 페이로드, 시그니쳐 중 시그니쳐가 해석 불가능할 때

            }
        }
    }

    @Override
    public Jws<Claims> verifyAccessToken(String accessToken) {
        if (!accessToken.isEmpty()) {
            try {
                return Jwts.parserBuilder().setSigningKey(ACCESS_PRIVATE_KEY).build()
                        .parseClaimsJws(accessToken);
            } catch (ExpiredJwtException e) {//헤더, 페이로드, 시그니쳐 중 시그니쳐가 해석 불가능할 때
                return null;
            }
        }
        return null;
    }

    @Override
    public String createAccessToken(String userId, List<GrantedAuthority> roles) {
        Claims claims = Jwts.claims().setSubject(userId).setSubject(roles.toString());
        Date createdDate = new Date();
        Date expirationDate = new Date(createdDate.getTime() + accessExpirationMillis);

        return Jwts.builder()
                .setSubject(userId)
                .setClaims(claims)
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

    private Key getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return Keys.hmacShaKeyFor(publicKey.getBytes(StandardCharsets.UTF_8));
//        KeyFactory keyFactory = KeyFactory.getInstance("EC");
//        return keyFactory.generatePublic(new X509EncodedKeySpec(publicKey.getBytes(StandardCharsets.UTF_8)));
    }

    private Key getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return Keys.hmacShaKeyFor(privateKey.getBytes(StandardCharsets.UTF_8));
//        KeyFactory keyFactory = KeyFactory.getInstance("EC");
//        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKey.getBytes(StandardCharsets.UTF_8)));
    }
}
