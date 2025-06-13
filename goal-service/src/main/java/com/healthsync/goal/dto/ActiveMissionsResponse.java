package com.healthsync.goal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 활성 미션 응답 DTO 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "활성 미션 응답")
public class ActiveMissionsResponse {
    
    @Schema(description = "일일 미션 목록")
    private List<DailyMission> dailyMissions;
    
    @Schema(description = "총 미션 수")
    private int totalMissions;
    
    @Schema(description = "오늘 완료된 미션 수")
    private int todayCompletedCount;
    
    @Schema(description = "완료율 (%)")
    private double completionRate;
}
