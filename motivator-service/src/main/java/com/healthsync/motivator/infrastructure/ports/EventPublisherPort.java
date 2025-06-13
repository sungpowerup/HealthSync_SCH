package com.healthsync.motivator.infrastructure.ports;

/**
 * 이벤트 발행을 위한 포트 인터페이스입니다.
 * Clean Architecture의 Domain 계층에서 정의합니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
public interface EventPublisherPort {
    
    /**
     * 독려 메시지 전송 이벤트를 발행합니다.
     * 
     * @param userId 사용자 ID
     * @param messageType 메시지 유형
     */
    void publishEncouragementSentEvent(String userId, String messageType);
}
