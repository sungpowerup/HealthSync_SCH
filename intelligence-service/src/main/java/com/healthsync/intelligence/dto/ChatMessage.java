package com.healthsync.intelligence.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 채팅 메시지 DTO 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "채팅 메시지")
public class ChatMessage {
    
    @Schema(description = "메시지 역할 (user/assistant)")
    private String role;
    
    @Schema(description = "메시지 내용")
    private String content;
    
    @Schema(description = "메시지 전송 시간")
    private String timestamp;
}
