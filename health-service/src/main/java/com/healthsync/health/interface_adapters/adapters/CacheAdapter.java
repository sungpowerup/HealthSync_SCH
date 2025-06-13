package com.healthsync.health.interface_adapters.adapters;

import com.healthsync.health.dto.HealthHistoryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * Redis 캐시와의 통신을 담당하는 어댑터 클래스입니다.
 * Clean Architecture의 Interface Adapter 계층에 해당합니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CacheAdapter {
    
    private final RedisTemplate<String, Object> redisTemplate;
    
    /**
     * 건강검진 이력을 캐시에서 조회합니다.
     * 
     * @param cacheKey 캐시 키
     * @return 건강검진 이력 (캐시 미스 시 null)
     */
    public HealthHistoryResponse getHealthHistory(String cacheKey) {
        try {
            return (HealthHistoryResponse) redisTemplate.opsForValue().get(cacheKey);
        } catch (Exception e) {
            log.warn("캐시 조회 실패: key={}, error={}", cacheKey, e.getMessage());
            return null;
        }
    }
    
    /**
     * 건강검진 이력을 캐시에 저장합니다.
     * 
     * @param cacheKey 캐시 키
     * @param response 건강검진 이력
     */
    public void cacheHealthHistory(String cacheKey, HealthHistoryResponse response) {
        try {
            redisTemplate.opsForValue().set(cacheKey, response, Duration.ofHours(1));
            log.info("건강검진 이력 캐시 저장: key={}", cacheKey);
        } catch (Exception e) {
            log.warn("캐시 저장 실패: key={}, error={}", cacheKey, e.getMessage());
        }
    }
}
