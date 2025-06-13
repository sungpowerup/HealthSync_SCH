package com.healthsync.intelligence.infrastructure.ports;

import com.healthsync.intelligence.dto.UserProfile;

/**
 * User Service와의 통신을 위한 포트 인터페이스입니다.
 * Clean Architecture의 Domain 계층에서 정의합니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
public interface UserServicePort {
    
    /**
     * 사용자 프로필을 조회합니다.
     * 
     * @param userId 사용자 ID
     * @return 사용자 프로필
     */
    UserProfile getUserProfile(String userId);
}
