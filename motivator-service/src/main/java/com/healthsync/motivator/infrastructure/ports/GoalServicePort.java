package com.healthsync.motivator.infrastructure.ports;

import com.healthsync.motivator.dto.DailyProgress;
import com.healthsync.motivator.dto.UserMissionStatus;

import java.util.List;

/**
 * Goal Service와의 통신을 위한 포트 인터페이스입니다.
 * Clean Architecture의 Domain 계층에서 정의합니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
public interface GoalServicePort {
    
    /**
     * 사용자의 일일 진행 상황을 조회합니다.
     * 
     * @param userId 사용자 ID
     * @return 일일 진행 상황
     */
    DailyProgress getUserDailyProgress(String userId);
    
    /**
     * 활성 미션이 있는 모든 사용자를 조회합니다.
     * 
     * @return 사용자 미션 상태 목록
     */
    List<UserMissionStatus> getAllUsersWithActiveMissions();
}
