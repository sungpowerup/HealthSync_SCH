package com.healthsync.goal.infrastructure.ports;

import com.healthsync.goal.dto.CompletionData;

import java.util.List;

/**
 * 이벤트 발행을 위한 포트 인터페이스입니다.
 * Clean Architecture의 Domain 계층에서 정의합니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
public interface EventPublisherPort {
    
    /**
     * 목표 설정 이벤트를 발행합니다.
     * 
     * @param userId 사용자 ID
     * @param missionIds 미션 ID 목록
     */
    void publishGoalSetEvent(String userId, List<String> missionIds);
    
    /**
     * 미션 완료 이벤트를 발행합니다.
     * 
     * @param userId 사용자 ID
     * @param missionId 미션 ID
     * @param completionData 완료 데이터
     */
    void publishMissionCompleteEvent(String userId, String missionId, CompletionData completionData);
    
    /**
     * 미션 재설정 이벤트를 발행합니다.
     * 
     * @param userId 사용자 ID
     * @param resetReason 재설정 이유
     */
    void publishMissionResetEvent(String userId, String resetReason);
}
