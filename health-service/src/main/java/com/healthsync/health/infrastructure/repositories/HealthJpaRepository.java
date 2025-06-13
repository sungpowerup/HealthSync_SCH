package com.healthsync.health.infrastructure.repositories;

import com.healthsync.health.infrastructure.entities.HealthCheckupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 건강검진 데이터를 위한 JPA 리포지토리입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Repository
public interface HealthJpaRepository extends JpaRepository<HealthCheckupEntity, Long> {
    
    /**
     * 사용자의 최신 건강검진 데이터를 조회합니다.
     * 
     * @param userId 사용자 ID
     * @return 최신 건강검진 데이터
     */
    Optional<HealthCheckupEntity> findTopByUserIdOrderByCheckupDateDesc(String userId);
    
    /**
     * 사용자의 건강검진 이력을 조회합니다.
     * 
     * @param userId 사용자 ID
     * @return 건강검진 이력 목록
     */
    List<HealthCheckupEntity> findByUserIdOrderByCheckupDateDesc(String userId);
}
