package com.healthsync.motivator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 배치 알림 응답 DTO 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "배치 알림 응답")
public class BatchNotificationResponse {
    
    @Schema(description = "배치 ID")
    private String batchId;
    
    @Schema(description = "처리된 사용자 수")
    private int processedCount;
    
    @Schema(description = "성공한 알림 수")
    private int successCount;
    
    @Schema(description = "실패한 알림 수")
    private int failedCount;
    
    @Schema(description = "다음 스케줄 시간")
    private String nextScheduledTime;
}
