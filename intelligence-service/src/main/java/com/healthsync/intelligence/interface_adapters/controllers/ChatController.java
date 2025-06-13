package com.healthsync.intelligence.interface_adapters.controllers;

import com.healthsync.common.dto.ApiResponse;
import com.healthsync.intelligence.application_services.ChatConsultationUseCase;
import com.healthsync.intelligence.dto.ChatRequest;
import com.healthsync.intelligence.dto.ChatResponse;
import com.healthsync.intelligence.dto.ChatHistoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * AI 채팅 상담 관련 API를 제공하는 컨트롤러입니다.
 * Clean Architecture의 Interface Adapter 계층에 해당합니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/intelligence/chat")
@RequiredArgsConstructor
@Tag(name = "AI 채팅 상담", description = "AI 기반 건강 상담 채팅 API")
public class ChatController {
    
    private final ChatConsultationUseCase chatConsultationUseCase;
    
    /**
     * AI와 건강 상담 채팅을 진행합니다.
     * 
     * @param request 채팅 요청
     * @return AI 응답
     */
    @PostMapping("/consultation")
    @Operation(summary = "AI 채팅 상담", description = "AI와 건강 관련 상담을 진행합니다")
    public ResponseEntity<ApiResponse<ChatResponse>> processChatConsultation(@Valid @RequestBody ChatRequest request) {
        log.info("AI 채팅 상담 요청: sessionId={}, userId={}", request.getSessionId(), request.getUserId());
        
        ChatResponse response = chatConsultationUseCase.processChatConsultation(request);
        
        log.info("AI 채팅 상담 완료: sessionId={}, responseType={}", response.getSessionId(), response.getResponseType());
        return ResponseEntity.ok(ApiResponse.success("AI 상담이 완료되었습니다.", response));
    }
    
    /**
     * 채팅 기록을 조회합니다.
     * 
     * @param sessionId 세션 ID
     * @param messageLimit 조회할 메시지 수
     * @return 채팅 기록
     */
    @GetMapping("/history")
    @Operation(summary = "채팅 기록 조회", description = "세션의 채팅 기록을 조회합니다")
    public ResponseEntity<ApiResponse<ChatHistoryResponse>> getChatHistory(
            @RequestParam String sessionId,
            @RequestParam(defaultValue = "20") int messageLimit) {
        log.info("채팅 기록 조회 요청: sessionId={}, limit={}", sessionId, messageLimit);
        
        ChatHistoryResponse response = chatConsultationUseCase.getChatHistory(sessionId, messageLimit);
        
        log.info("채팅 기록 조회 완료: sessionId={}, messageCount={}", sessionId, response.getTotalMessageCount());
        return ResponseEntity.ok(ApiResponse.success("채팅 기록 조회가 완료되었습니다.", response));
    }
}
