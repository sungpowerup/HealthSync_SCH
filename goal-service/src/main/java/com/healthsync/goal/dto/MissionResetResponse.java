package com.healthsync.goal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 미션 재설정 응답 DTO 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "미션 재설정 응답")
public class MissionResetResponse {
    
    @Schema(description = "응답 메시지")
    private String message;
    
    @Schema(description = "새로운 추천 미션 목록")
    private List<RecommendedMission> newRecommendations;
    
    @Schema(description = "재설정 완료 시간")
    private String resetCompletedAt;
}
