package com.healthsync.intelligence.interface_adapters.controllers;

import com.healthsync.common.dto.ApiResponse;
import com.healthsync.intelligence.application_services.HealthAnalysisUseCase;
import com.healthsync.intelligence.dto.HealthDiagnosisResponse;
import com.healthsync.intelligence.dto.MissionRecommendationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * AI 건강 분석 관련 API를 제공하는 컨트롤러입니다.
 * Clean Architecture의 Interface Adapter 계층에 해당합니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/intelligence")
@RequiredArgsConstructor
@Tag(name = "AI 건강 분석", description = "AI 기반 건강 분석 및 미션 추천 API")
public class AnalysisController {
    
    private final HealthAnalysisUseCase healthAnalysisUseCase;
    
    /**
     * 건강검진 결과를 AI로 3줄 요약 진단합니다.
     * 
     * @param userId 사용자 ID
     * @return AI 진단 결과
     */
    @GetMapping("/health/diagnosis")
    @Operation(summary = "건강검진 결과 AI 3줄 요약", description = "사용자의 건강검진 결과를 AI가 3줄로 요약하여 진단합니다")
    public ResponseEntity<ApiResponse<HealthDiagnosisResponse>> generateHealthDiagnosis(@RequestParam String userId) {
        log.info("AI 건강 진단 요청: userId={}", userId);
        
        HealthDiagnosisResponse response = healthAnalysisUseCase.generateHealthDiagnosis(userId);
        
        log.info("AI 건강 진단 완료: userId={}, healthScore={}", userId, response.getHealthScore());
        return ResponseEntity.ok(ApiResponse.success("AI 건강 진단이 완료되었습니다.", response));
    }
    
    /**
     * AI 기반 미션을 추천합니다.
     * 
     * @param userId 사용자 ID
     * @return 추천 미션 목록
     */
    @PostMapping("/missions/recommend")
    @Operation(summary = "AI 기반 미션 추천", description = "사용자의 건강 상태와 직업군을 고려하여 AI가 미션을 추천합니다")
    public ResponseEntity<ApiResponse<MissionRecommendationResponse>> recommendMissions(@RequestParam String userId) {
        log.info("AI 미션 추천 요청: userId={}", userId);
        
        MissionRecommendationResponse response = healthAnalysisUseCase.recommendMissions(userId);
        
        log.info("AI 미션 추천 완료: userId={}, recommendedCount={}", userId, response.getTotalRecommended());
        return ResponseEntity.ok(ApiResponse.success("AI 미션 추천이 완료되었습니다.", response));
    }
}
