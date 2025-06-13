package com.healthsync.health.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 건강검진 연동 응답 DTO 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "건강검진 연동 응답")
public class HealthSyncResponse {
    
    @Schema(description = "연동 상태")
    private String syncStatus;
    
    @Schema(description = "응답 메시지")
    private String message;
    
    @Schema(description = "AI 분석 준비 완료 여부")
    private boolean isReadyForAnalysis;
    
    @Schema(description = "연동 완료 시간")
    private String syncedAt;
}
