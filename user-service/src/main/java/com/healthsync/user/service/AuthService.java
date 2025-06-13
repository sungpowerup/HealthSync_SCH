package com.healthsync.user.service;

import com.healthsync.common.exception.AuthenticationException;
import com.healthsync.common.util.JwtUtil;
import com.healthsync.user.acl.GoogleOAuthClient;
import com.healthsync.user.dto.GoogleUserInfo;
import com.healthsync.user.dto.LoginResponse;
import com.healthsync.user.entity.UserEntity;
import com.healthsync.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * 인증 서비스 클래스입니다.
 * Google OAuth 인증 및 JWT 토큰 생성을 담당합니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    
    private final GoogleOAuthClient googleOAuthClient;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    
    /**
     * Google OAuth 인증을 처리합니다.
     * 
     * @param authCode Google OAuth 인증 코드
     * @return 로그인 응답 (JWT 토큰, 신규 회원 여부)
     */
    public LoginResponse authenticateWithGoogle(String authCode) {
        try {
            // Google OAuth 인증 정보 획득
            GoogleUserInfo googleUserInfo = googleOAuthClient.getGoogleUserInfo(authCode);
            
            // 기존 사용자 확인
            Optional<UserEntity> existingUser = userRepository.findByGoogleId(googleUserInfo.getId());
            
            if (existingUser.isPresent()) {
                // 기존 사용자 - 로그인 처리
                UserEntity user = existingUser.get();
                user.updateLastLoginAt();
                
                String accessToken = jwtUtil.generateAccessToken(user.getId().toString());
                String refreshToken = jwtUtil.generateRefreshToken(user.getId().toString());
                
                return LoginResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .isNewUser(false)
                        .userId(user.getId().toString())
                        .expiresIn(3600)
                        .build();
            } else {
                // 신규 사용자 - 임시 사용자 생성
                UserEntity newUser = UserEntity.builder()
                        .googleId(googleUserInfo.getId())
                        .email(googleUserInfo.getEmail())
                        .build();
                
                UserEntity savedUser = userRepository.save(newUser);
                
                String accessToken = jwtUtil.generateAccessToken(savedUser.getId().toString());
                String refreshToken = jwtUtil.generateRefreshToken(savedUser.getId().toString());
                
                return LoginResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .isNewUser(true)
                        .userId(savedUser.getId().toString())
                        .expiresIn(3600)
                        .build();
            }
            
        } catch (Exception e) {
            log.error("Google OAuth 인증 실패: {}", e.getMessage(), e);
            throw new AuthenticationException("Google 인증에 실패했습니다.");
        }
    }
    
    /**
     * 로그아웃을 처리합니다.
     * 
     * @param userId 사용자 ID
     */
    public void logout(String userId) {
        // 실제 구현에서는 Redis에서 토큰 블랙리스트 관리
        log.info("사용자 로그아웃 처리: userId={}", userId);
    }
}
