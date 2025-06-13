package com.healthsync.intelligence.application_services;

import com.healthsync.intelligence.domain.services.AiAnalysisDomainService;
import com.healthsync.intelligence.dto.HealthDiagnosisResponse;
import com.healthsync.intelligence.dto.MissionRecommendationResponse;
import com.healthsync.intelligence.dto.UserProfile;
import com.healthsync.intelligence.dto.HealthData;
import com.healthsync.intelligence.infrastructure.ports.HealthServicePort;
import com.healthsync.intelligence.infrastructure.ports.UserServicePort;
import com.healthsync.intelligence.infrastructure.ports.CachePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 건강 분석 유스케이스입니다.
 * Clean Architecture의 Application Service 계층에 해당합니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HealthAnalysisUseCase {
    
    private final AiAnalysisDomainService aiAnalysisDomainService;
    private final UserServicePort userServicePort;
    private final HealthServicePort healthServicePort;
    private final CachePort cachePort;
    
    /**
     * AI 건강 진단을 생성합니다.
     * 
     * @param userId 사용자 ID
     * @return 건강 진단 결과
     */
    public HealthDiagnosisResponse generateHealthDiagnosis(String userId) {
        log.info("AI 건강 진단 생성 시작: userId={}", userId);
        
        // 캐시 확인
        String cacheKey = "health_diagnosis:" + userId;
        HealthDiagnosisResponse cachedResponse = cachePort.getHealthDiagnosis(cacheKey);
        if (cachedResponse != null) {
            log.info("캐시에서 건강 진단 조회: userId={}", userId);
            return cachedResponse;
        }
        
        // 사용자 정보 조회
        UserProfile userProfile = userServicePort.getUserProfile(userId);
        
        // 건강 데이터 조회
        HealthData healthData = healthServicePort.getHealthData(userId);
        
        // AI 진단 생성
        HealthDiagnosisResponse response = aiAnalysisDomainService.generateHealthDiagnosis(userProfile, healthData);
        
        // 캐시 저장 (2시간)
        cachePort.cacheHealthDiagnosis(cacheKey, response, 7200);
        
        log.info("AI 건강 진단 생성 완료: userId={}, healthScore={}", userId, response.getHealthScore());
        return response;
    }
    
    /**
     * AI 미션 추천을 생성합니다.
     * 
     * @param userId 사용자 ID
     * @return 미션 추천 결과
     */
    public MissionRecommendationResponse recommendMissions(String userId) {
        log.info("AI 미션 추천 생성 시작: userId={}", userId);
        
        // 캐시 확인
        String cacheKey = "mission_recommendations:" + userId;
        MissionRecommendationResponse cachedResponse = cachePort.getMissionRecommendations(cacheKey);
        if (cachedResponse != null) {
            log.info("캐시에서 미션 추천 조회: userId={}", userId);
            return cachedResponse;
        }
        
        // 사용자 정보 조회
        UserProfile userProfile = userServicePort.getUserProfile(userId);
        
        // 건강 데이터 조회
        HealthData healthData = healthServicePort.getHealthData(userId);
        
        // AI 미션 추천 생성
        MissionRecommendationResponse response = aiAnalysisDomainService.generateMissionRecommendations(userProfile, healthData);
        
        // 캐시 저장 (2시간)
        cachePort.cacheMissionRecommendations(cacheKey, response, 7200);
        
        log.info("AI 미션 추천 생성 완료: userId={}, recommendedCount={}", userId, response.getTotalRecommended());
        return response;
    }
}
