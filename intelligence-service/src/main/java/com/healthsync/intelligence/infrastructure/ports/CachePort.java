package com.healthsync.intelligence.infrastructure.ports;

import com.healthsync.intelligence.dto.HealthDiagnosisResponse;
import com.healthsync.intelligence.dto.MissionRecommendationResponse;
import com.healthsync.intelligence.dto.ChatMessage;

import java.util.List;

/**
 * 캐시와의 통신을 위한 포트 인터페이스입니다.
 * Clean Architecture의 Domain 계층에서 정의합니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
public interface CachePort {
    
    /**
     * 건강 진단 결과를 캐시에서 조회합니다.
     * 
     * @param cacheKey 캐시 키
     * @return 건강 진단 결과 (캐시 미스 시 null)
     */
    HealthDiagnosisResponse getHealthDiagnosis(String cacheKey);
    
    /**
     * 건강 진단 결과를 캐시에 저장합니다.
     * 
     * @param cacheKey 캐시 키
     * @param response 건강 진단 결과
     * @param expireSeconds 만료 시간(초)
     */
    void cacheHealthDiagnosis(String cacheKey, HealthDiagnosisResponse response, int expireSeconds);
    
    /**
     * 미션 추천 결과를 캐시에서 조회합니다.
     * 
     * @param cacheKey 캐시 키
     * @return 미션 추천 결과 (캐시 미스 시 null)
     */
    MissionRecommendationResponse getMissionRecommendations(String cacheKey);
    
    /**
     * 미션 추천 결과를 캐시에 저장합니다.
     * 
     * @param cacheKey 캐시 키
     * @param response 미션 추천 결과
     * @param expireSeconds 만료 시간(초)
     */
    void cacheMissionRecommendations(String cacheKey, MissionRecommendationResponse response, int expireSeconds);
    
    /**
     * 최근 채팅을 캐시에 저장합니다.
     * 
     * @param sessionId 세션 ID
     * @param userMessage 사용자 메시지
     * @param aiResponse AI 응답
     */
    void cacheRecentChat(String sessionId, String userMessage, String aiResponse);
    
    /**
     * 최근 채팅 기록을 캐시에서 조회합니다.
     * 
     * @param sessionId 세션 ID
     * @return 최근 채팅 기록 (캐시 미스 시 null)
     */
    List<ChatMessage> getRecentChatHistory(String sessionId);
}
