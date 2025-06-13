package com.healthsync.goal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 추천 미션 DTO 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "추천 미션")
public class RecommendedMission {
    
    @Schema(description = "미션 ID")
    private String missionId;
    
    @Schema(description = "미션 제목")
    private String title;
    
    @Schema(description = "미션 설명")
    private String description;
    
    @Schema(description = "미션 카테고리")
    private String category;
}
