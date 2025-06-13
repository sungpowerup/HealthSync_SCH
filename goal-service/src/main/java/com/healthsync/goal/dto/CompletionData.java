package com.healthsync.goal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 완료 데이터 DTO 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "완료 데이터")
public class CompletionData {
    
    @Schema(description = "사용자 ID")
    private String userId;
    
    @Schema(description = "미션 ID")
    private String missionId;
    
    @Schema(description = "완료 여부")
    private boolean completed;
    
    @Schema(description = "완료 시간")
    private String completedAt;
    
    @Schema(description = "메모")
    private String notes;
}
