package com.healthsync.intelligence.infrastructure.ports;

import com.healthsync.intelligence.dto.HealthData;

/**
 * Health Service와의 통신을 위한 포트 인터페이스입니다.
 * Clean Architecture의 Domain 계층에서 정의합니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
public interface HealthServicePort {
    
    /**
     * 사용자의 건강 데이터를 조회합니다.
     * 
     * @param userId 사용자 ID
     * @return 건강 데이터
     */
    HealthData getHealthData(String userId);
}
