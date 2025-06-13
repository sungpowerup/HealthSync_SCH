package com.healthsync.intelligence.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * AI 건강 진단 응답 DTO 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "AI 건강 진단 응답")
public class HealthDiagnosisResponse {
    
    @Schema(description = "3줄 요약 진단 결과")
    private List<String> threeSentenceSummary;
    
    @Schema(description = "건강 점수 (0-100)")
    private int healthScore;
    
    @Schema(description = "위험 수준")
    private String riskLevel;
    
    @Schema(description = "직업군별 고려사항")
    private String occupationConsiderations;
    
    @Schema(description = "분석 완료 시간")
    private String analysisTimestamp;
    
    @Schema(description = "신뢰도 점수 (0-1)")
    private double confidenceScore;
}
