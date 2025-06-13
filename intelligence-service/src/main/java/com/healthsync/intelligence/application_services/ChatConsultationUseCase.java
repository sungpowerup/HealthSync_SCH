package com.healthsync.intelligence.application_services;

import com.healthsync.intelligence.domain.services.ChatDomainService;
import com.healthsync.intelligence.domain.repositories.ChatHistoryRepository;
import com.healthsync.intelligence.dto.ChatRequest;
import com.healthsync.intelligence.dto.ChatResponse;
import com.healthsync.intelligence.dto.ChatHistoryResponse;
import com.healthsync.intelligence.dto.ChatMessage;
import com.healthsync.intelligence.infrastructure.ports.CachePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 채팅 상담 유스케이스입니다.
 * Clean Architecture의 Application Service 계층에 해당합니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ChatConsultationUseCase {
    
    private final ChatDomainService chatDomainService;
    private final ChatHistoryRepository chatHistoryRepository;
    private final CachePort cachePort;
    
    /**
     * AI 채팅 상담을 처리합니다.
     * 
     * @param request 채팅 요청
     * @return AI 응답
     */
    public ChatResponse processChatConsultation(ChatRequest request) {
        log.info("AI 채팅 상담 처리 시작: sessionId={}", request.getSessionId());
        
        // 입력 검증
        chatDomainService.validateChatInput(request.getMessage());
        
        // 채팅 기록 조회
        List<ChatMessage> chatHistory = chatHistoryRepository.getChatHistory(request.getSessionId(), 10);
        
        // AI 응답 생성
        ChatResponse response = chatDomainService.generateChatResponse(request, chatHistory);
        
        // 채팅 기록 저장
        chatHistoryRepository.saveChatMessage(request.getSessionId(), request.getUserId(), 
                request.getMessage(), response.getResponse());
        
        // 캐시에 최근 채팅 저장
        cachePort.cacheRecentChat(request.getSessionId(), request.getMessage(), response.getResponse());
        
        log.info("AI 채팅 상담 처리 완료: sessionId={}", response.getSessionId());
        return response;
    }
    
    /**
     * 채팅 기록을 조회합니다.
     * 
     * @param sessionId 세션 ID
     * @param messageLimit 조회할 메시지 수
     * @return 채팅 기록
     */
    public ChatHistoryResponse getChatHistory(String sessionId, int messageLimit) {
        log.info("채팅 기록 조회: sessionId={}, limit={}", sessionId, messageLimit);
        
        // 캐시에서 최근 기록 조회
        List<ChatMessage> recentMessages = cachePort.getRecentChatHistory(sessionId);
        
        if (recentMessages != null && recentMessages.size() >= messageLimit) {
            return ChatHistoryResponse.builder()
                    .sessionId(sessionId)
                    .messages(recentMessages.subList(0, messageLimit))
                    .totalMessageCount(recentMessages.size())
                    .cacheExpiration("1 hour")
                    .build();
        }
        
        // DB에서 전체 기록 조회
        List<ChatMessage> allMessages = chatHistoryRepository.getChatHistory(sessionId, messageLimit);
        
        ChatHistoryResponse response = ChatHistoryResponse.builder()
                .sessionId(sessionId)
                .messages(allMessages)
                .totalMessageCount(allMessages.size())
                .cacheExpiration("N/A")
                .build();
        
        log.info("채팅 기록 조회 완료: sessionId={}, messageCount={}", sessionId, allMessages.size());
        return response;
    }
}
