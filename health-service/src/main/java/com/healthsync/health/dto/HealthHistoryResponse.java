package com.healthsync.health.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 건강검진 이력 응답 DTO 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "건강검진 이력 응답")
public class HealthHistoryResponse {
    
    @Schema(description = "사용자 정보")
    private UserInfo userInfo;
    
    @Schema(description = "건강검진 기록 목록")
    private List<CheckupRecord> checkupRecords;
    
    @Schema(description = "차트 데이터")
    private Object chartData;
    
    @Schema(description = "정상치 범위 기준")
    private String normalRangeReference;
}
