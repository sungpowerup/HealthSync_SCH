package com.healthsync.intelligence.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * AI 채팅 응답 DTO 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "AI 채팅 응답")
public class ChatResponse {
    
    @Schema(description = "AI 응답 메시지")
    private String response;
    
    @Schema(description = "세션 ID")
    private String sessionId;
    
    @Schema(description = "응답 시간")
    private String timestamp;
    
    @Schema(description = "추천 질문 목록")
    private List<String> suggestedQuestions;
    
    @Schema(description = "응답 유형")
    private String responseType;
}
