package com.healthsync.motivator.infrastructure.repositories;

import com.healthsync.motivator.infrastructure.entities.NotificationLogEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 알림 로그를 위한 JPA 리포지토리입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Repository
public interface NotificationLogJpaRepository extends JpaRepository<NotificationLogEntity, Long> {
    
    /**
     * 사용자의 알림 로그를 시간 역순으로 조회합니다.
     * 
     * @param userId 사용자 ID
     * @param pageable 페이징 정보
     * @return 알림 로그 목록
     */
    List<NotificationLogEntity> findByUserIdOrderBySentAtDesc(String userId, Pageable pageable);
    
    /**
     * 배치 ID로 알림 로그를 조회합니다.
     * 
     * @param batchId 배치 ID
     * @return 알림 로그 목록
     */
    List<NotificationLogEntity> findByBatchId(String batchId);
}
