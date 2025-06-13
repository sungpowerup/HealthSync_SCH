package com.healthsync.motivator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 사용자 알림 컨텍스트 DTO 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "사용자 알림 컨텍스트")
public class UserNotificationContext {
    
    @Schema(description = "사용자 ID")
    private String userId;
    
    @Schema(description = "완료율")
    private double completionRate;
    
    @Schema(description = "연속 실패 일수")
    private int consecutiveFailures;
    
    @Schema(description = "마지막 활동 시간")
    private LocalDateTime lastActiveTime;
    
    @Schema(description = "총 미션 수")
    private int totalMissions;
    
    @Schema(description = "완료된 미션 수")
    private int completedMissions;
}
