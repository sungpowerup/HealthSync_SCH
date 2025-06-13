package com.healthsync.goal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

/**
 * 미션 재설정 요청 DTO 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "미션 재설정 요청")
public class MissionResetRequest {
    
    @NotBlank(message = "사용자 ID는 필수입니다.")
    @Schema(description = "사용자 ID")
    private String userId;
    
    @NotBlank(message = "재설정 이유는 필수입니다.")
    @Schema(description = "재설정 이유")
    private String reason;
    
    @Schema(description = "현재 미션 ID 목록")
    private List<String> currentMissionIds;
}
