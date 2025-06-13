package com.healthsync.goal.domain.repositories;

import com.healthsync.goal.dto.*;

import java.util.List;

/**
 * 목표 데이터 저장소 인터페이스입니다.
 * Clean Architecture의 Domain 계층에서 정의합니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
public interface GoalRepository {
    
    /**
     * 목표 설정을 저장합니다.
     * 
     * @param request 미션 선택 요청
     * @return 목표 ID
     */
    String saveGoalSettings(MissionSelectionRequest request);
    
    /**
     * 사용자의 활성 미션을 조회합니다.
     * 
     * @param userId 사용자 ID
     * @return 활성 미션 목록
     */
    List<DailyMission> findActiveMissionsByUserId(String userId);
    
    /**
     * 현재 미션을 비활성화합니다.
     * 
     * @param userId 사용자 ID
     */
    void deactivateCurrentMissions(String userId);
    
    /**
     * 미션 완료를 기록합니다.
     * 
     * @param missionId 미션 ID
     * @param request 미션 완료 요청
     */
    void recordMissionCompletion(String missionId, MissionCompleteRequest request);
    
    /**
     * 총 완료 횟수를 조회합니다.
     * 
     * @param userId 사용자 ID
     * @param missionId 미션 ID
     * @return 총 완료 횟수
     */
    int getTotalCompletedCount(String userId, String missionId);
    
    /**
     * 기간별 미션 이력을 조회합니다.
     * 
     * @param userId 사용자 ID
     * @param startDate 시작일
     * @param endDate 종료일
     * @param missionIds 미션 ID 목록
     * @return 미션 통계 목록
     */
    List<MissionStats> findMissionHistoryByPeriod(String userId, String startDate, String endDate, String missionIds);
}
