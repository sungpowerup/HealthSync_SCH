package com.healthsync.goal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 미션 이력 응답 DTO 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "미션 이력 응답")
public class MissionHistoryResponse {
    
    @Schema(description = "전체 달성률 (%)")
    private double totalAchievementRate;
    
    @Schema(description = "기간 내 달성률 (%)")
    private double periodAchievementRate;
    
    @Schema(description = "최고 연속 달성 일수")
    private int bestStreak;
    
    @Schema(description = "미션별 통계")
    private List<MissionStats> missionStats;
    
    @Schema(description = "차트 데이터")
    private Object chartData;
    
    @Schema(description = "조회 기간")
    private Period period;
    
    @Schema(description = "인사이트")
    private List<String> insights;
}
