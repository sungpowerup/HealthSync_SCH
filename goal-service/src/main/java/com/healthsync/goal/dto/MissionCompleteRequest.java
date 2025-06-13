package com.healthsync.goal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 미션 완료 요청 DTO 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "미션 완료 요청")
public class MissionCompleteRequest {
    
    @NotBlank(message = "사용자 ID는 필수입니다.")
    @Schema(description = "사용자 ID")
    private String userId;
    
    @NotNull(message = "완료 여부는 필수입니다.")
    @Schema(description = "완료 여부")
    private boolean completed;
    
    @Schema(description = "완료 시간")
    private String completedAt;
    
    @Schema(description = "메모")
    private String notes;
}
