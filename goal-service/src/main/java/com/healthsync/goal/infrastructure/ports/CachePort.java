package com.healthsync.goal.infrastructure.ports;

import com.healthsync.goal.dto.ActiveMissionsResponse;
import com.healthsync.goal.dto.MissionHistoryResponse;

/**
 * 캐시와의 통신을 위한 포트 인터페이스입니다.
 * Clean Architecture의 Domain 계층에서 정의합니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
public interface CachePort {
    
    /**
     * 활성 미션을 캐시에서 조회합니다.
     * 
     * @param userId 사용자 ID
     * @return 활성 미션 응답 (캐시 미스 시 null)
     */
    ActiveMissionsResponse getActiveMissions(String userId);
    
    /**
     * 활성 미션을 캐시에 저장합니다.
     * 
     * @param userId 사용자 ID
     * @param response 활성 미션 응답
     */
    void cacheActiveMissions(String userId, ActiveMissionsResponse response);
    
    /**
     * 미션 이력을 캐시에서 조회합니다.
     * 
     * @param cacheKey 캐시 키
     * @return 미션 이력 응답 (캐시 미스 시 null)
     */
    MissionHistoryResponse getMissionHistory(String cacheKey);
    
    /**
     * 미션 이력을 캐시에 저장합니다.
     * 
     * @param cacheKey 캐시 키
     * @param response 미션 이력 응답
     */
    void cacheMissionHistory(String cacheKey, MissionHistoryResponse response);
    
    /**
     * 사용자 미션 캐시를 무효화합니다.
     * 
     * @param userId 사용자 ID
     */
    void invalidateUserMissionCache(String userId);
}
