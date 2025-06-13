package com.healthsync.health.infrastructure.repositories;

import com.healthsync.health.domain.model.HealthCheckup;
import com.healthsync.health.domain.repositories.HealthRepository;
import com.healthsync.health.infrastructure.entities.HealthCheckupEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 건강 데이터 저장소 구현체입니다.
 * Clean Architecture의 Infrastructure 계층에 해당합니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class HealthRepositoryImpl implements HealthRepository {
    
    private final HealthJpaRepository healthJpaRepository;
    
    @Override
    public HealthCheckup saveHealthCheckup(HealthCheckup healthCheckup) {
        HealthCheckupEntity entity = HealthCheckupEntity.fromDomain(healthCheckup);
        HealthCheckupEntity savedEntity = healthJpaRepository.save(entity);
        return savedEntity.toDomain();
    }
    
    @Override
    public Optional<HealthCheckup> findLatestHealthCheckup(String userId) {
        return healthJpaRepository.findTopByUserIdOrderByCheckupDateDesc(userId)
                .map(HealthCheckupEntity::toDomain);
    }
    
    @Override
    public List<HealthCheckup> findHealthCheckupHistory(String userId, int limit) {
        return healthJpaRepository.findByUserIdOrderByCheckupDateDesc(userId)
                .stream()
                .limit(limit)
                .map(HealthCheckupEntity::toDomain)
                .toList();
    }
}
