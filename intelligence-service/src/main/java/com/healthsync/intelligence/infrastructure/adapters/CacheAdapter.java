package com.healthsync.intelligence.infrastructure.adapters;

import com.healthsync.intelligence.dto.HealthDiagnosisResponse;
import com.healthsync.intelligence.dto.MissionRecommendationResponse;
import com.healthsync.intelligence.dto.ChatMessage;
import com.healthsync.intelligence.infrastructure.ports.CachePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    public HealthDiagnosisResponse getHealthDiagnosis(String cacheKey) {
        try {
            return (HealthDiagnosisResponse) redisTemplate.opsForValue().get(cacheKey);
        } catch (Exception e) {
            log.warn("건강 진단 캐시 조회 실패: key={}, error={}", cacheKey, e.getMessage());
            return null;
        }
    }
    
    @Override
    public void cacheHealthDiagnosis(String cacheKey, HealthDiagnosisResponse response, int expireSeconds) {
        try {
            redisTemplate.opsForValue().set(cacheKey, response, Duration.ofSeconds(expireSeconds));
            log.info("건강 진단 캐시 저장: key={}, expireSeconds={}", cacheKey, expireSeconds);
        } catch (Exception e) {
            log.warn("건강 진단 캐시 저장 실패: key={}, error={}", cacheKey, e.getMessage());
        }
    }
    
    @Override
    public MissionRecommendationResponse getMissionRecommendations(String cacheKey) {
        try {
            return (MissionRecommendationResponse) redisTemplate.opsForValue().get(cacheKey);
        } catch (Exception e) {
            log.warn("미션 추천 캐시 조회 실패: key={}, error={}", cacheKey, e.getMessage());
            return null;
        }
    }
    
    @Override
    public void cacheMissionRecommendations(String cacheKey, MissionRecommendationResponse response, int expireSeconds) {
        try {
            redisTemplate.opsForValue().set(cacheKey, response, Duration.ofSeconds(expireSeconds));
            log.info("미션 추천 캐시 저장: key={}, expireSeconds={}", cacheKey, expireSeconds);
        } catch (Exception e) {
            log.warn("미션 추천 캐시 저장 실패: key={}, error={}", cacheKey, e.getMessage());
        }
    }
    
    @Override
    public void cacheRecentChat(String sessionId, String userMessage, String aiResponse) {
        try {
            String chatKey = "chat:recent:" + sessionId;
            
            // 사용자 메시지 추가
            ChatMessage userChatMessage = ChatMessage.builder()
                    .role("user")
                    .content(userMessage)
                    .timestamp(LocalDateTime.now().toString())
                    .build();
            
            // AI 응답 추가
            ChatMessage aiChatMessage = ChatMessage.builder()
                    .role("assistant")
                    .content(aiResponse)
                    .timestamp(LocalDateTime.now().toString())
                    .build();
            
            // Redis List에 추가 (최신순으로 저장)
            redisTemplate.opsForList().leftPush(chatKey, aiChatMessage);
            redisTemplate.opsForList().leftPush(chatKey, userChatMessage);
            
            // 최대 50개까지 유지
            redisTemplate.opsForList().trim(chatKey, 0, 49);
            
            // 1시간 후 만료
            redisTemplate.expire(chatKey, Duration.ofHours(1));
            
            log.info("최근 채팅 캐시 저장: sessionId={}", sessionId);
        } catch (Exception e) {
            log.warn("최근 채팅 캐시 저장 실패: sessionId={}, error={}", sessionId, e.getMessage());
        }
    }
    
    @Override
    public List<ChatMessage> getRecentChatHistory(String sessionId) {
        try {
            String chatKey = "chat:recent:" + sessionId;
            
            List<Object> cachedMessages = redisTemplate.opsForList().range(chatKey, 0, -1);
            
            if (cachedMessages == null || cachedMessages.isEmpty()) {
                return null;
            }
            
            List<ChatMessage> messages = new ArrayList<>();
            for (Object obj : cachedMessages) {
                if (obj instanceof ChatMessage) {
                    messages.add((ChatMessage) obj);
                }
            }
            
            log.info("최근 채팅 캐시 조회: sessionId={}, messageCount={}", sessionId, messages.size());
            return messages;
            
        } catch (Exception e) {
            log.warn("최근 채팅 캐시 조회 실패: sessionId={}, error={}", sessionId, e.getMessage());
            return null;
        }
    }
}
