package com.healthsync.intelligence.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 미션 정보 DTO 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "미션 정보")
public class Mission {
    
    @Schema(description = "미션 ID")
    private String missionId;
    
    @Schema(description = "미션 제목")
    private String title;
    
    @Schema(description = "미션 설명")
    private String description;
    
    @Schema(description = "미션 카테고리")
    private String category;
    
    @Schema(description = "난이도")
    private String difficulty;
    
    @Schema(description = "건강 효과")
    private String healthBenefit;
    
    @Schema(description = "직업군 관련성")
    private String occupationRelevance;
    
    @Schema(description = "예상 소요시간(분)")
    private int estimatedTimeMinutes;
}
