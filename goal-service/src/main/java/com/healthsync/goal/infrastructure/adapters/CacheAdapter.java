package com.healthsync.goal.infrastructure.adapters;

import com.healthsync.goal.dto.ActiveMissionsResponse;
import com.healthsync.goal.dto.MissionHistoryResponse;
import com.healthsync.goal.infrastructure.ports.CachePort;
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
    public ActiveMissionsResponse getActiveMissions(String userId) {
        try {
            String cacheKey = "active_missions:" + userId;
            return (ActiveMissionsResponse) redisTemplate.opsForValue().get(cacheKey);
        } catch (Exception e) {
            log.warn("활성 미션 캐시 조회 실패: userId={}, error={}", userId, e.getMessage());
            return null;
        }
    }
    
    @Override
    public void cacheActiveMissions(String userId, ActiveMissionsResponse response) {
        try {
            String cacheKey = "active_missions:" + userId;
            redisTemplate.opsForValue().set(cacheKey, response, Duration.ofMinutes(30));
            log.info("활성 미션 캐시 저장: userId={}", userId);
        } catch (Exception e) {
            log.warn("활성 미션 캐시 저장 실패: userId={}, error={}", userId, e.getMessage());
        }
    }
    
    @Override
    public MissionHistoryResponse getMissionHistory(String cacheKey) {
        try {
            return (MissionHistoryResponse) redisTemplate.opsForValue().get(cacheKey);
        } catch (Exception e) {
            log.warn("미션 이력 캐시 조회 실패: key={}, error={}", cacheKey, e.getMessage());
            return null;
        }
    }
    
    @Override
    public void cacheMissionHistory(String cacheKey, MissionHistoryResponse response) {
        try {
            redisTemplate.opsForValue().set(cacheKey, response, Duration.ofHours(1));
            log.info("미션 이력 캐시 저장: key={}", cacheKey);
        } catch (Exception e) {
            log.warn("미션 이력 캐시 저장 실패: key={}, error={}", cacheKey, e.getMessage());
        }
    }
    
    @Override
    public void invalidateUserMissionCache(String userId) {
        try {
            String activeMissionKey = "active_missions:" + userId;
            String historyKeyPattern = "mission_history:" + userId + ":*";
            
            // 활성 미션 캐시 삭제
            redisTemplate.delete(activeMissionKey);
            
            // 미션 이력 캐시 삭제 (패턴 매칭)
            redisTemplate.delete(redisTemplate.keys(historyKeyPattern));
            
            log.info("사용자 미션 캐시 무효화 완료: userId={}", userId);
        } catch (Exception e) {
            log.warn("사용자 미션 캐시 무효화 실패: userId={}, error={}", userId, e.getMessage());
        }
    }
}
