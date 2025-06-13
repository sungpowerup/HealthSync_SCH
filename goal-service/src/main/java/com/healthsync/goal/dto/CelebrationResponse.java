package com.healthsync.goal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 축하 응답 DTO 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "축하 응답")
public class CelebrationResponse {
    
    @Schema(description = "축하 메시지")
    private String congratsMessage;
    
    @Schema(description = "성취 배지")
    private String achievementBadge;
    
    @Schema(description = "건강 효과")
    private String healthBenefit;
    
    @Schema(description = "다음 마일스톤")
    private String nextMilestone;
}
