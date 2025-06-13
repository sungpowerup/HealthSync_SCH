package com.healthsync.motivator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 독려 메시지 응답 DTO 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "독려 메시지 응답")
public class EncouragementResponse {
    
    @Schema(description = "독려 메시지")
    private String message;
    
    @Schema(description = "동기부여 유형")
    private String motivationType;
    
    @Schema(description = "최적 타이밍")
    private String timing;
    
    @Schema(description = "개인화된 팁")
    private String personalizedTip;
    
    @Schema(description = "우선순위")
    private String priority;
}
