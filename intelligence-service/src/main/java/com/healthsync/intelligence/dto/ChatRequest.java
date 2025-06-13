package com.healthsync.intelligence.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * AI 채팅 요청 DTO 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "AI 채팅 요청")
public class ChatRequest {
    
    @NotBlank(message = "메시지는 필수입니다.")
    @Size(max = 1000, message = "메시지는 1000자 이내로 입력해주세요.")
    @Schema(description = "채팅 메시지")
    private String message;
    
    @NotBlank(message = "세션 ID는 필수입니다.")
    @Schema(description = "채팅 세션 ID")
    private String sessionId;
    
    @Schema(description = "컨텍스트 정보")
    private String context;
    
    @NotBlank(message = "사용자 ID는 필수입니다.")
    @Schema(description = "사용자 ID")
    private String userId;
}
