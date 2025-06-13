package com.healthsync.user.controller;

import com.healthsync.common.dto.ApiResponse;
import com.healthsync.user.dto.UserRegistrationRequest;
import com.healthsync.user.dto.UserRegistrationResponse;
import com.healthsync.user.dto.UserProfileResponse;
import com.healthsync.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * 사용자 관리 API를 제공하는 컨트롤러입니다.
 * 회원가입, 프로필 조회/수정을 담당합니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "사용자", description = "사용자 관리 관련 API")
public class UserController {
    
    private final UserService userService;
    
    /**
     * 신규 사용자 회원가입을 처리합니다.
     * 
     * @param request 회원가입 요청 정보 (이름, 생년월일, 직업군)
     * @return 회원가입 결과
     */
    @PostMapping("/register")
    @Operation(summary = "회원 정보 등록", description = "신규 사용자의 기본 정보를 등록합니다")
    public ResponseEntity<ApiResponse<UserRegistrationResponse>> register(@Valid @RequestBody UserRegistrationRequest request) {
        log.info("회원가입 요청: name={}, occupation={}", request.getName(), request.getOccupation());
        
        UserRegistrationResponse response = userService.registerUser(request);
        
        log.info("회원가입 완료: userId={}", response.getUserId());
        return ResponseEntity.ok(ApiResponse.success("회원가입이 완료되었습니다.", response));
    }
    
    /**
     * 사용자 프로필 정보를 조회합니다.
     * 
     * @param userId 사용자 ID
     * @return 사용자 프로필 정보
     */
    @GetMapping("/profile")
    @Operation(summary = "사용자 기본 정보 조회", description = "사용자의 프로필 정보를 조회합니다")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getProfile(@RequestParam String userId) {
        log.info("프로필 조회 요청: userId={}", userId);
        
        UserProfileResponse response = userService.getUserProfile(userId);
        
        log.info("프로필 조회 완료: userId={}", userId);
        return ResponseEntity.ok(ApiResponse.success("프로필 조회가 완료되었습니다.", response));
    }
}
