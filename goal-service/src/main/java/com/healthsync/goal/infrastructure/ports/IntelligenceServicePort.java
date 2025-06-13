package com.healthsync.goal.infrastructure.ports;

import com.healthsync.goal.dto.CelebrationResponse;
import com.healthsync.goal.dto.Mission;

import java.util.List;

/**
 * Intelligence Service와의 통신을 위한 포트 인터페이스입니다.
 * Clean Architecture의 Domain 계층에서 정의합니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
public interface IntelligenceServicePort {
    
    /**
     * 축하 메시지를 요청합니다.
     * 
     * @param userId 사용자 ID
     * @param missionId 미션 ID
     * @param streakDays 연속 달성 일수
     * @return 축하 응답
     */
    CelebrationResponse requestCelebrationMessage(String userId, String missionId, int streakDays);
    
    /**
     * 새로운 미션 추천을 요청합니다.
     * 
     * @param userId 사용자 ID
     * @param resetReason 재설정 이유
     * @return 추천 미션 목록
     */
    List<Mission> requestNewMissionRecommendations(String userId, String resetReason);
}
