package com.healthsync.goal.infrastructure.repositories;

import com.healthsync.goal.infrastructure.entities.UserMissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 사용자 미션을 위한 JPA 리포지토리입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Repository
public interface UserMissionJpaRepository extends JpaRepository<UserMissionEntity, Long> {
    
    /**
     * 사용자의 활성 미션을 조회합니다.
     * 
     * @param userId 사용자 ID
     * @return 활성 미션 목록
     */
    List<UserMissionEntity> findByUserIdAndActiveTrue(Long userId);
    
    /**
     * 사용자의 특정 미션을 조회합니다.
     * 
     * @param userId 사용자 ID
     * @param missionId 미션 ID
     * @return 사용자 미션
     */
    Optional<UserMissionEntity> findByUserIdAndMissionId(Long userId, String missionId);
}
