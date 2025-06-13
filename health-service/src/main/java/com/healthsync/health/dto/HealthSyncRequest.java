package com.healthsync.health.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;

/**
 * 건강검진 연동 요청 DTO 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "건강검진 연동 요청")
public class HealthSyncRequest {
    
    @NotBlank(message = "사용자 ID는 필수입니다.")
    @Schema(description = "사용자 ID")
    private String userId;
}
