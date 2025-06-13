package com.healthsync.intelligence.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * AI 미션 추천 응답 DTO 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "AI 미션 추천 응답")
public class MissionRecommendationResponse {
    
    @Schema(description = "추천 미션 목록")
    private List<Mission> missions;
    
    @Schema(description = "추천 이유")
    private String recommendationReason;
    
    @Schema(description = "총 추천 미션 수")
    private int totalRecommended;
}
