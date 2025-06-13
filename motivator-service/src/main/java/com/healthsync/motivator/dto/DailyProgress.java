package com.healthsync.motivator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 일일 진행 상황 DTO 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "일일 진행 상황")
public class DailyProgress {
    
    @Schema(description = "현재 연속 달성 일수")
    private int currentStreak;
    
    @Schema(description = "주간 완료율")
    private double weeklyCompletionRate;
    
    @Schema(description = "오늘 완료된 미션 수")
    private int todayCompletedCount;
    
    @Schema(description = "오늘 총 미션 수")
    private int todayTotalCount;
}
