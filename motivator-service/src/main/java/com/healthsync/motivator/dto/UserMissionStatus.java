package com.healthsync.motivator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 사용자 미션 상태 DTO 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "사용자 미션 상태")
public class UserMissionStatus {
    
    @Schema(description = "사용자 ID")
    private String userId;
    
    @Schema(description = "총 미션 수")
    private int totalMissions;
    
    @Schema(description = "완료된 미션 수")
    private int completedMissions;
    
    @Schema(description = "마지막 활동 시간")
    private LocalDateTime lastActiveTime;
}
