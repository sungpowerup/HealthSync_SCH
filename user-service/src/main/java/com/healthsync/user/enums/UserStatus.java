package com.healthsync.user.enums;

/**
 * 사용자 상태를 나타내는 열거형입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
public enum UserStatus {
    
    /**
     * 대기 상태 - OAuth 인증만 완료된 상태
     */
    PENDING,
    
    /**
     * 활성 상태 - 회원가입까지 완료된 상태
     */
    ACTIVE,
    
    /**
     * 비활성 상태 - 탈퇴 또는 정지된 상태
     */
    INACTIVE
}
