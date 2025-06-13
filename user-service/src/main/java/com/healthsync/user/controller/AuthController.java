package com.healthsync.user.controller;

import com.healthsync.common.dto.ApiResponse;
import com.healthsync.user.dto.LoginRequest;
import com.healthsync.user.dto.LoginResponse;
import com.healthsync.user.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * 인증 관련 API를 제공하는 컨트롤러입니다.
 * Google OAuth 로그인 처리를 담당합니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "인증", description = "사용자 인증 관련 API")
public class AuthController {
    
    private final AuthService authService;
    
    /**
     * Google OAuth 로그인을 처리합니다.
     * 
     * @param request 로그인 요청 정보 (Google Auth Code)
     * @return 로그인 결과 (JWT 토큰, 신규 회원 여부)
     */
    @PostMapping("/login")
    @Operation(summary = "구글 SSO 로그인", description = "Google OAuth 인증 코드를 통해 로그인을 처리합니다")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        log.info("Google OAuth 로그인 요청: authCode={}", request.getCode());
        
        LoginResponse response = authService.authenticateWithGoogle(request.getCode());
        
        log.info("로그인 완료: userId={}, isNewUser={}", response.getUserId(), response.isNewUser());
        return ResponseEntity.ok(ApiResponse.success("로그인이 완료되었습니다.", response));
    }
    
    /**
     * 로그아웃을 처리합니다.
     * 
     * @param userId 사용자 ID
     * @return 로그아웃 결과
     */
    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "사용자 로그아웃을 처리합니다")
    public ResponseEntity<ApiResponse<Void>> logout(@RequestParam String userId) {
        log.info("로그아웃 요청: userId={}", userId);
        
        authService.logout(userId);
        
        log.info("로그아웃 완료: userId={}", userId);
        return ResponseEntity.ok(ApiResponse.success("로그아웃이 완료되었습니다.", null));
    }
}
