package com.healthsync.goal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 미션 통계 DTO 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "미션 통계")
public class MissionStats {
    
    @Schema(description = "미션 ID")
    private String missionId;
    
    @Schema(description = "미션 제목")
    private String title;
    
    @Schema(description = "달성률 (%)")
    private double achievementRate;
    
    @Schema(description = "완료 일수")
    private int completedDays;
    
    @Schema(description = "총 일수")
    private int totalDays;
}
