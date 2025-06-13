package com.healthsync.intelligence.infrastructure.repositories;

import com.healthsync.intelligence.domain.repositories.ChatHistoryRepository;
import com.healthsync.intelligence.dto.ChatMessage;
import com.healthsync.intelligence.infrastructure.entities.ChatMessageEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 채팅 기록 저장소 구현체입니다.
 * Clean Architecture의 Infrastructure 계층에 해당합니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class ChatHistoryRepositoryImpl implements ChatHistoryRepository {
    
    private final ChatMessageJpaRepository chatMessageJpaRepository;
    
    @Override
    public void saveChatMessage(String sessionId, String userId, String userMessage, String aiResponse) {
        // 사용자 메시지 저장
        ChatMessageEntity userMessageEntity = ChatMessageEntity.builder()
                .sessionId(sessionId)
                .userId(userId)
                .role("user")
                .content(userMessage)
                .timestamp(LocalDateTime.now())
                .build();
        
        chatMessageJpaRepository.save(userMessageEntity);
        
        // AI 응답 저장
        ChatMessageEntity aiMessageEntity = ChatMessageEntity.builder()
                .sessionId(sessionId)
                .userId(userId)
                .role("assistant")
                .content(aiResponse)
                .timestamp(LocalDateTime.now())
                .build();
        
        chatMessageJpaRepository.save(aiMessageEntity);
        
        log.info("채팅 메시지 저장 완료: sessionId={}", sessionId);
    }
    
    @Override
    public List<ChatMessage> getChatHistory(String sessionId, int limit) {
        PageRequest pageRequest = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "timestamp"));
        
        List<ChatMessageEntity> entities = chatMessageJpaRepository
                .findBySessionIdOrderByTimestampDesc(sessionId, pageRequest);
        
        return entities.stream()
                .map(this::entityToDto)
                .toList();
    }
    
    @Override
    public void deleteChatHistory(String sessionId) {
        chatMessageJpaRepository.deleteBySessionId(sessionId);
        log.info("채팅 기록 삭제 완료: sessionId={}", sessionId);
    }
    
    /**
     * 엔티티를 DTO로 변환합니다.
     * 
     * @param entity 채팅 메시지 엔티티
     * @return 채팅 메시지 DTO
     */
    private ChatMessage entityToDto(ChatMessageEntity entity) {
        return ChatMessage.builder()
                .role(entity.getRole())
                .content(entity.getContent())
                .timestamp(entity.getTimestamp().toString())
                .build();
    }
}
