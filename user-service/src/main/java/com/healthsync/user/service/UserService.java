package com.healthsync.user.service;

import com.healthsync.common.exception.BusinessException;
import com.healthsync.common.util.DateUtil;
import com.healthsync.user.dto.UserRegistrationRequest;
import com.healthsync.user.dto.UserRegistrationResponse;
import com.healthsync.user.dto.UserProfileResponse;
import com.healthsync.user.entity.UserEntity;
import com.healthsync.user.enums.UserStatus;
import com.healthsync.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 사용자 관리 서비스 클래스입니다.
 * 회원가입, 프로필 관리를 담당합니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    
    private final UserRepository userRepository;
    
    /**
     * 사용자 회원가입을 처리합니다.
     * 
     * @param request 회원가입 요청 정보
     * @return 회원가입 응답
     */
    public UserRegistrationResponse registerUser(UserRegistrationRequest request) {
        // 사용자 존재 여부 확인 (이미 임시 계정이 있는 경우)
        UserEntity user = userRepository.findById(Long.parseLong(request.getUserId()))
                .orElseThrow(() -> new BusinessException("USER_NOT_FOUND", "사용자를 찾을 수 없습니다."));
        
        // 사용자 정보 업데이트
        user.updateProfile(
                request.getName(),
                request.getBirthDate(),
                request.getOccupation()
        );
        user.updateStatus(UserStatus.ACTIVE);
        
        UserEntity savedUser = userRepository.save(user);
        
        return UserRegistrationResponse.builder()
                .userId(savedUser.getId().toString())
                .message("회원가입이 완료되었습니다.")
                .status("SUCCESS")
                .build();
    }
    
    /**
     * 사용자 프로필을 조회합니다.
     * 
     * @param userId 사용자 ID
     * @return 사용자 프로필 정보
     */
    @Transactional(readOnly = true)
    public UserProfileResponse getUserProfile(String userId) {
        UserEntity user = userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new BusinessException("USER_NOT_FOUND", "사용자를 찾을 수 없습니다."));
        
        int age = user.getBirthDate() != null ? DateUtil.calculateAge(user.getBirthDate()) : 0;
        
        return UserProfileResponse.builder()
                .userId(user.getId().toString())
                .name(user.getName())
                .age(age)
                .occupation(user.getOccupation())
                .registeredAt(user.getCreatedAt().toString())
                .lastLoginAt(user.getLastLoginAt() != null ? user.getLastLoginAt().toString() : null)
                .build();
    }
}
