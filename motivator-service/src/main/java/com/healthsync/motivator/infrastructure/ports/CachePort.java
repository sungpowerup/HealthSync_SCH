package com.healthsync.motivator.infrastructure.ports;

import com.healthsync.motivator.dto.EncouragementResponse;

/**
 * 캐시와의 통신을 위한 포트 인터페이스입니다.
 * Clean Architecture의 Domain 계층에서 정의합니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
public interface CachePort {
    
    /**
     * 캐시된 독려 메시지를 조회합니다.
     * 
     * @param cacheKey 캐시 키
     * @return 독려 응답 (캐시 미스 시 null)
     */
    EncouragementResponse getCachedEncouragementMessage(String cacheKey);
    
    /**
     * 독려 메시지를 캐시에 저장합니다.
     * 
     * @param cacheKey 캐시 키
     * @param response 독려 응답
     */
    void cacheEncouragementMessage(String cacheKey, EncouragementResponse response);
    
    /**
     * 배치 메시지를 저장합니다.
     * 
     * @param userId 사용자 ID
     * @param message 메시지
     */
    void storeBatchMessage(String userId, String message);
}
