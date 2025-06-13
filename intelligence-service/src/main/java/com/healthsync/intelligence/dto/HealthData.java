package com.healthsync.intelligence.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 건강 데이터 DTO 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "건강 데이터")
public class HealthData {
    
    @Schema(description = "BMI")
    private double bmi;
    
    @Schema(description = "혈압")
    private String bloodPressure;
    
    @Schema(description = "콜레스테롤")
    private String cholesterol;
    
    @Schema(description = "혈당")
    private String bloodSugar;
    
    @Schema(description = "검진일")
    private String checkupDate;
}
