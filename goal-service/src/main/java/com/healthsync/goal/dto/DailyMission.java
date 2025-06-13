package com.healthsync.goal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 일일 미션 DTO 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "일일 미션")
public class DailyMission {
    
    @Schema(description = "미션 ID")
    private String missionId;
    
    @Schema(description = "미션 제목")
    private String title;
    
    @Schema(description = "미션 설명")
    private String description;
    
    @Schema(description = "미션 상태")
    private String status;
    
    @Schema(description = "오늘 완료 여부")
    private boolean completedToday;
    
    @Schema(description = "연속 달성 일수")
    private int streakDays;
    
    @Schema(description = "다음 알림 시간")
    private String nextReminderTime;
}
