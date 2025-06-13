package com.healthsync.goal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 목표 설정 응답 DTO 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "목표 설정 응답")
public class GoalSetupResponse {
    
    @Schema(description = "목표 ID")
    private String goalId;
    
    @Schema(description = "선택된 미션 목록")
    private List<SelectedMission> selectedMissions;
    
    @Schema(description = "응답 메시지")
    private String message;
    
    @Schema(description = "설정 완료 시간")
    private String setupCompletedAt;
}
