package com.soolsul.soolsulserver.common.jwt;

import com.soolsul.soolsulserver.domain.auth.Authority;
import com.soolsul.soolsulserver.domain.auth.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;

@Component
public class JwtTokenProvider implements TokenProvider{

    private static PublicKey ACCESS_PUBLIC_KEY;
    private static PublicKey REFRESH_PUBLIC_KEY;
    private static PrivateKey ACCESS_PRIVATE_KEY;
    private static PrivateKey REFRESH_PRIVATE_KEY;

    @Value("${jwt.access.expirationMs}")
    private static Long accessExpirationMillis;

    @Value("${jwt.refresh.expirationDay}")
    private static Long refreshExpirationDay;

    private PublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        return keyFactory.generatePublic(new X509EncodedKeySpec(publicKey.getBytes(StandardCharsets.UTF_8)));
    }

    private PrivateKey getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKey.getBytes(StandardCharsets.UTF_8)));
    }

    public JwtTokenProvider(
            @Value("${jwt.access.private.key}")String accessPrivateKey,
            @Value("${jwt.refresh.private.key}")String refreshPrivateKey,
            @Value("${jwt.access.public.key}")String accessPublicKey,
            @Value("${jwt.refresh.public.key}")String refreshPublicKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        ACCESS_PRIVATE_KEY = getPrivateKey(accessPrivateKey);
        ACCESS_PUBLIC_KEY = getPublicKey(accessPublicKey);
        REFRESH_PRIVATE_KEY = getPrivateKey(refreshPrivateKey);
        REFRESH_PUBLIC_KEY = getPublicKey(refreshPublicKey);

    }


    @Override
    public JwtToken issue(Long userId, List<Authority> roles) {
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
        if(!refreshToken.isEmpty()){
            try{
               Jwts.parserBuilder().setSigningKey(REFRESH_PUBLIC_KEY).build()
                        .parseClaimsJws(refreshToken);
            }catch(ExpiredJwtException e){//헤더, 페이로드, 시그니쳐 중 시그니쳐가 해석 불가능할 때

            }
        }
    }

    @Override
    public Jws<Claims> verifyAccessToken(String accessToken) {
        if(!accessToken.isEmpty()){
            try{
                return Jwts.parserBuilder().setSigningKey(ACCESS_PRIVATE_KEY).build()
                        .parseClaimsJws(accessToken);
            }catch(ExpiredJwtException e){//헤더, 페이로드, 시그니쳐 중 시그니쳐가 해석 불가능할 때
                return null;
            }
        }
        return null;
    }

    @Override
    public String createAccessToken(Long userId, List<Authority> roles) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiredAt = now.plus(accessExpirationMillis, ChronoUnit.MILLIS);
        return Jwts.builder()
                .setSubject(userId.toString())
                .claim("userId", userId)
                .claim("roles", roles)
                .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(expiredAt.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(ACCESS_PRIVATE_KEY)
                .compact();
    }

    @Override
    public String createRefreshToken() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiredAt = now.plus(refreshExpirationDay, ChronoUnit.DAYS);
        return Jwts.builder()
                .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(expiredAt.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(REFRESH_PRIVATE_KEY)
                .compact();
    }
}
