package com.healthsync.goal.infrastructure.repositories;

import com.healthsync.goal.infrastructure.entities.MissionCompletionHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

/**
 * 미션 완료 기록을 위한 JPA 리포지토리입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Repository
public interface MissionCompletionJpaRepository extends JpaRepository<MissionCompletionHistoryEntity, Long> {
    
    /**
     * 사용자의 미션 완료 횟수를 조회합니다.
     * 
     * @param userId 사용자 ID
     * @param missionId 미션 ID
     * @return 완료 횟수
     */
    int countByUserIdAndMissionId(Long userId, String missionId);
    
    /**
     * 특정 날짜의 미션 완료 여부를 확인합니다.
     * 
     * @param userId 사용자 ID
     * @param missionId 미션 ID
     * @param date 날짜
     * @return 완료 여부
     */
    boolean existsByUserIdAndMissionIdAndCompletedDate(Long userId, String missionId, LocalDate date);
}
