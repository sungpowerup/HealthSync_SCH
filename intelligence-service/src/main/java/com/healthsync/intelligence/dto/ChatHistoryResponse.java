package com.healthsync.intelligence.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 채팅 기록 응답 DTO 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "채팅 기록 응답")
public class ChatHistoryResponse {
    
    @Schema(description = "세션 ID")
    private String sessionId;
    
    @Schema(description = "채팅 메시지 목록")
    private List<ChatMessage> messages;
    
    @Schema(description = "총 메시지 수")
    private int totalMessageCount;
    
    @Schema(description = "캐시 만료 시간")
    private String cacheExpiration;
}
