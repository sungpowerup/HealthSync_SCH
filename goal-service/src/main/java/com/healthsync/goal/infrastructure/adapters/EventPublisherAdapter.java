package com.healthsync.goal.infrastructure.adapters;

import com.healthsync.goal.dto.CompletionData;
import com.healthsync.goal.infrastructure.ports.EventPublisherPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

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
    public void publishGoalSetEvent(String userId, List<String> missionIds) {
        try {
            log.info("목표 설정 이벤트 발행: userId={}, missionCount={}", userId, missionIds.size());
            
            // 실제 구현에서는 이벤트 브로커에 발행
            // GoalSetEvent event = GoalSetEvent.builder()
            //         .userId(userId)
            //         .missionIds(missionIds)
            //         .timestamp(LocalDateTime.now())
            //         .build();
            // serviceBusTemplate.send("goal-set-topic", event);
            
            log.info("목표 설정 이벤트 발행 완료: userId={}", userId);
        } catch (Exception e) {
            log.error("목표 설정 이벤트 발행 실패: userId={}, error={}", userId, e.getMessage(), e);
        }
    }
    
    @Override
    public void publishMissionCompleteEvent(String userId, String missionId, CompletionData completionData) {
        try {
            log.info("미션 완료 이벤트 발행: userId={}, missionId={}", userId, missionId);
            
            // 실제 구현에서는 이벤트 브로커에 발행
            // MissionCompleteEvent event = MissionCompleteEvent.builder()
            //         .userId(userId)
            //         .missionId(missionId)
            //         .completionData(completionData)
            //         .timestamp(LocalDateTime.now())
            //         .build();
            // serviceBusTemplate.send("mission-complete-topic", event);
            
            log.info("미션 완료 이벤트 발행 완료: userId={}, missionId={}", userId, missionId);
        } catch (Exception e) {
            log.error("미션 완료 이벤트 발행 실패: userId={}, missionId={}, error={}", userId, missionId, e.getMessage(), e);
        }
    }
    
    @Override
    public void publishMissionResetEvent(String userId, String resetReason) {
        try {
            log.info("미션 재설정 이벤트 발행: userId={}, reason={}", userId, resetReason);
            
            // 실제 구현에서는 이벤트 브로커에 발행
            // MissionResetEvent event = MissionResetEvent.builder()
            //         .userId(userId)
            //         .resetReason(resetReason)
            //         .timestamp(LocalDateTime.now())
            //         .build();
            // serviceBusTemplate.send("mission-reset-topic", event);
            
            log.info("미션 재설정 이벤트 발행 완료: userId={}", userId);
        } catch (Exception e) {
            log.error("미션 재설정 이벤트 발행 실패: userId={}, error={}", userId, e.getMessage(), e);
        }
    }
}
