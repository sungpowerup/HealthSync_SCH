package com.healthsync.motivator.infrastructure.adapters;

import com.healthsync.motivator.infrastructure.ports.EventPublisherPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 이벤트 발행을 담당하는 어댑터 클래스입니다.
 * Clean Architecture의 Infrastructure 계층에 해당합니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class EventPublisherAdapter implements EventPublisherPort {
    
    // 실제 구현에서는 Spring Cloud Stream 또는 Azure Service Bus 사용
    // private final ServiceBusTemplate serviceBusTemplate;
    
    @Override
    public void publishEncouragementSentEvent(String userId, String messageType) {
        try {
            log.info("독려 메시지 전송 이벤트 발행: userId={}, messageType={}", userId, messageType);
            
            // 실제 구현에서는 이벤트 브로커에 발행
            // EncouragementSentEvent event = EncouragementSentEvent.builder()
            //         .userId(userId)
            //         .messageType(messageType)
            //         .timestamp(LocalDateTime.now())
            //         .build();
            // serviceBusTemplate.send("encouragement-sent-topic", event);
            
            log.info("독려 메시지 전송 이벤트 발행 완료: userId={}", userId);
        } catch (Exception e) {
            log.error("독려 메시지 전송 이벤트 발행 실패: userId={}, error={}", userId, e.getMessage(), e);
        }
    }
}
