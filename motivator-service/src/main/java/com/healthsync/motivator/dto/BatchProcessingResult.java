package com.healthsync.motivator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 배치 처리 결과 DTO 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "배치 처리 결과")
public class BatchProcessingResult {
    
    @Schema(description = "배치 ID")
    private String batchId;
    
    @Schema(description = "처리된 수")
    private int processedCount;
    
    @Schema(description = "성공한 수")
    private int successCount;
    
    @Schema(description = "실패한 수")
    private int failedCount;
}
