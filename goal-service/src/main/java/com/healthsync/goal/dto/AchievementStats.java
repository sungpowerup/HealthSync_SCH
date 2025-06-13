package com.healthsync.goal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 달성 통계 DTO 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "달성 통계")
public class AchievementStats {
    
    @Schema(description = "전체 달성률 (%)")
    private double totalAchievementRate;
    
    @Schema(description = "기간 내 달성률 (%)")
    private double periodAchievementRate;
    
    @Schema(description = "최고 연속 달성 일수")
    private int bestStreak;
    
    @Schema(description = "완료 일수")
    private int completedDays;
    
    @Schema(description = "총 일수")
    private int totalDays;
}
