package com.healthsync.health.domain.repositories;

import com.healthsync.health.domain.model.HealthCheckup;

import java.util.List;
import java.util.Optional;

/**
 * 건강 데이터 저장소 인터페이스입니다.
 * Clean Architecture의 Domain 계층에서 정의합니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
public interface HealthRepository {
    
    /**
     * 건강검진 데이터를 저장합니다.
     * 
     * @param healthCheckup 건강검진 데이터
     * @return 저장된 건강검진 데이터
     */
    HealthCheckup saveHealthCheckup(HealthCheckup healthCheckup);
    
    /**
     * 사용자의 최신 건강검진 데이터를 조회합니다.
     * 
     * @param userId 사용자 ID
     * @return 최신 건강검진 데이터
     */
    Optional<HealthCheckup> findLatestHealthCheckup(String userId);
    
    /**
     * 사용자의 건강검진 이력을 조회합니다.
     * 
     * @param userId 사용자 ID
     * @param limit 조회할 최대 건수
     * @return 건강검진 이력 목록
     */
    List<HealthCheckup> findHealthCheckupHistory(String userId, int limit);
}
