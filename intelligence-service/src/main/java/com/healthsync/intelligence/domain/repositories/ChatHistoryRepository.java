package com.healthsync.intelligence.domain.repositories;

import com.healthsync.intelligence.dto.ChatMessage;

import java.util.List;

/**
 * 채팅 기록 저장소 인터페이스입니다.
 * Clean Architecture의 Domain 계층에서 정의합니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
public interface ChatHistoryRepository {
    
    /**
     * 채팅 메시지를 저장합니다.
     * 
     * @param sessionId 세션 ID
     * @param userId 사용자 ID
     * @param userMessage 사용자 메시지
     * @param aiResponse AI 응답
     */
    void saveChatMessage(String sessionId, String userId, String userMessage, String aiResponse);
    
    /**
     * 채팅 기록을 조회합니다.
     * 
     * @param sessionId 세션 ID
     * @param limit 조회할 최대 메시지 수
     * @return 채팅 메시지 목록
     */
    List<ChatMessage> getChatHistory(String sessionId, int limit);
    
    /**
     * 세션의 채팅 기록을 삭제합니다.
     * 
     * @param sessionId 세션 ID
     */
    void deleteChatHistory(String sessionId);
}
