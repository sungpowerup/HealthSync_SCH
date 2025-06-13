package com.healthsync.intelligence.infrastructure.repositories;

import com.healthsync.intelligence.infrastructure.entities.ChatMessageEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 채팅 메시지를 위한 JPA 리포지토리입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Repository
public interface ChatMessageJpaRepository extends JpaRepository<ChatMessageEntity, Long> {
    
    /**
     * 세션 ID로 채팅 기록을 시간 역순으로 조회합니다.
     * 
     * @param sessionId 세션 ID
     * @param pageable 페이징 정보
     * @return 채팅 메시지 목록
     */
    List<ChatMessageEntity> findBySessionIdOrderByTimestampDesc(String sessionId, Pageable pageable);
    
    /**
     * 세션 ID로 채팅 기록을 삭제합니다.
     * 
     * @param sessionId 세션 ID
     */
    void deleteBySessionId(String sessionId);
}
