package com.healthsync.motivator.dto;

import com.healthsync.motivator.enums.MotivationType;
import com.healthsync.motivator.enums.UrgencyLevel;
import com.healthsync.motivator.enums.EngagementLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 진행 분석 DTO 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "진행 분석")
public class ProgressAnalysis {
    
    @Schema(description = "사용자 ID")
    private String userId;
    
    @Schema(description = "완료율")
    private double completionRate;
    
    @Schema(description = "완료된 미션 수")
    private int completedMissionsCount;
    
    @Schema(description = "전체 미션 수")
    private int totalMissionsCount;
    
    @Schema(description = "실패 포인트 목록")
    private List<String> failurePoints;
    
    @Schema(description = "진행 패턴")
    private String progressPattern;
    
    @Schema(description = "동기부여 유형")
    private MotivationType motivationType;
    
    @Schema(description = "긴급도 수준")
    private UrgencyLevel urgencyLevel;
    
    @Schema(description = "참여도 수준")
    private EngagementLevel engagementLevel;
    
    @Schema(description = "연속 달성 일수")
    private int streakDays;
    
    @Schema(description = "주간 완료율")
    private double weeklyCompletionRate;
}
