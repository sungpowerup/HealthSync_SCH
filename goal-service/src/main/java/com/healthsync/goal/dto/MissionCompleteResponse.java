package com.healthsync.goal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 미션 완료 응답 DTO 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "미션 완료 응답")
public class MissionCompleteResponse {
    
    @Schema(description = "응답 메시지")
    private String message;
    
    @Schema(description = "처리 상태")
    private String status;
    
    @Schema(description = "성취 메시지")
    private String achievementMessage;
    
    @Schema(description = "새로운 연속 달성 일수")
    private int newStreakDays;
    
    @Schema(description = "총 완료 횟수")
    private int totalCompletedCount;
    
    @Schema(description = "획득 포인트")
    private int earnedPoints;
}
