package com.healthsync.health.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 건강검진 기록 DTO 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "건강검진 기록")
public class CheckupRecord {
    
    @Schema(description = "검진 연도")
    private int year;
    
    @Schema(description = "검진 월")
    private int month;
    
    @Schema(description = "신장(cm)")
    private int height;
    
    @Schema(description = "체중(kg)")
    private int weight;
    
    @Schema(description = "허리둘레(cm)")
    private int waist;
    
    @Schema(description = "BMI")
    private double bmi;
    
    @Schema(description = "시력")
    private String vision;
    
    @Schema(description = "청력")
    private String hearing;
    
    @Schema(description = "혈압")
    private String bloodPressure;
    
    @Schema(description = "혈액검사 결과")
    private String bloodTest;
}