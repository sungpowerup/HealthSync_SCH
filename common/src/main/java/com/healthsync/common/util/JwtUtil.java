// common/src/main/java/com/healthsync/common/util/JwtUtil.java
package com.healthsync.common.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * JWT 토큰 유틸리티 클래스입니다.
 *
 * @author healthsync-team
 * @version 1.0
 */
@Slf4j
@Component
public class JwtUtil {

    private final SecretKey secretKey;
    private final long accessTokenValidityInMilliseconds;
    private final long refreshTokenValidityInMilliseconds;

    /**
     * JwtUtil 생성자
     *
     * @param secret JWT 시크릿 키
     * @param accessTokenValidityInMilliseconds 액세스 토큰 유효 시간
     * @param refreshTokenValidityInMilliseconds 리프레시 토큰 유효 시간
     */
    public JwtUtil(
            @Value("${jwt.secret:healthsync-default-secret-key-for-development-only}") String secret,
            @Value("${jwt.access-token.expire-length:3600000}") long accessTokenValidityInMilliseconds,
            @Value("${jwt.refresh-token.expire-length:604800000}") long refreshTokenValidityInMilliseconds) {

        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessTokenValidityInMilliseconds = accessTokenValidityInMilliseconds;
        this.refreshTokenValidityInMilliseconds = refreshTokenValidityInMilliseconds;
    }

    /**
     * 액세스 토큰을 생성합니다.
     *
     * @param userId 사용자 ID
     * @return 생성된 액세스 토큰
     */
    public String generateAccessToken(String userId) {
        return createToken(userId, accessTokenValidityInMilliseconds);
    }

    /**
     * 리프레시 토큰을 생성합니다.
     *
     * @param userId 사용자 ID
     * @return 생성된 리프레시 토큰
     */
    public String generateRefreshToken(String userId) {
        return createToken(userId, refreshTokenValidityInMilliseconds);
    }

    /**
     * 액세스 토큰을 생성합니다. (별칭 메서드)
     *
     * @param userId 사용자 ID
     * @return 생성된 액세스 토큰
     */
    public String createAccessToken(String userId) {
        return generateAccessToken(userId);
    }

    /**
     * 리프레시 토큰을 생성합니다. (별칭 메서드)
     *
     * @param userId 사용자 ID
     * @return 생성된 리프레시 토큰
     */
    public String createRefreshToken(String userId) {
        return generateRefreshToken(userId);
    }

    /**
     * 토큰을 생성합니다.
     *
     * @param userId 사용자 ID
     * @param validityInMilliseconds 유효 시간(밀리초)
     * @return 생성된 토큰
     */
    private String createToken(String userId, long validityInMilliseconds) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 토큰에서 사용자 ID를 추출합니다.
     *
     * @param token JWT 토큰
     * @return 사용자 ID
     */
    public String getUserId(String token) {
        return getClaims(token).getSubject();
    }

    /**
     * 토큰의 유효성을 검증합니다.
     *
     * @param token JWT 토큰
     * @return 유효성 여부
     */
    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            return false;
        }
    }

    /**
     * 토큰에서 Claims를 추출합니다.
     *
     * @param token JWT 토큰
     * @return Claims
     */
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 토큰의 만료 시간을 반환합니다.
     *
     * @param token JWT 토큰
     * @return 만료 시간
     */
    public LocalDateTime getExpirationDate(String token) {
        Date expiration = getClaims(token).getExpiration();
        return expiration.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}