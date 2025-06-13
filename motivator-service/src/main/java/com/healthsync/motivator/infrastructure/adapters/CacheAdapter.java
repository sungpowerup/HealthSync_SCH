package com.healthsync.motivator.infrastructure.adapters;

import com.healthsync.motivator.dto.EncouragementResponse;
import com.healthsync.motivator.infrastructure.ports.CachePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * Redis 캐시와의 통신을 담당하는 어댑터 클래스입니다.
 * Clean Architecture의 Infrastructure 계층에 해당합니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CacheAdapter implements CachePort {
    
    private final RedisTemplate<String, Object> redisTemplate;
    
    @Override
    public EncouragementResponse getCachedEncouragementMessage(String cacheKey) {
        try {
            return (EncouragementResponse) redisTemplate.opsForValue().get(cacheKey);
        } catch (Exception e) {
            log.warn("독려 메시지 캐시 조회 실패: key={}, error={}", cacheKey, e.getMessage());
            return null;
        }
    }
    
    @Override
    public void cacheEncouragementMessage(String cacheKey, EncouragementResponse response) {
        try {
            redisTemplate.opsForValue().set(cacheKey, response, Duration.ofMinutes(30));
            log.info("독려 메시지 캐시 저장: key={}", cacheKey);
        } catch (Exception e) {
            log.warn("독려 메시지 캐시 저장 실패: key={}, error={}", cacheKey, e.getMessage());
        }
    }
    
    @Override
    public void storeBatchMessage(String userId, String message) {
        try {
            String batchKey = "batch_message:" + userId;
            redisTemplate.opsForValue().set(batchKey, message, Duration.ofHours(24));
            log.info("배치 메시지 저장: userId={}", userId);
        } catch (Exception e) {
            log.warn("배치 메시지 저장 실패: userId={}, error={}", userId, e.getMessage());
        }
    }
}
