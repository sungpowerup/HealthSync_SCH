package com.healthsync.goal.infrastructure.repositories;

import com.healthsync.goal.infrastructure.entities.GoalSettingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 목표 설정을 위한 JPA 리포지토리입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Repository
public interface GoalJpaRepository extends JpaRepository<GoalSettingEntity, Long> {
    
    /**
     * 사용자의 활성 목표를 조회합니다.
     * 
     * @param userId 사용자 ID
     * @return 활성 목표 목록
     */
    List<GoalSettingEntity> findByUserIdAndStatus(Long userId, String status);
}
