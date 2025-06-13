package com.healthsync.motivator.infrastructure.repositories;

import com.healthsync.motivator.domain.repositories.NotificationRepository;
import com.healthsync.motivator.infrastructure.entities.NotificationLogEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 알림 데이터 저장소 구현체입니다.
 * Clean Architecture의 Infrastructure 계층에 해당합니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepository {
    
    private final NotificationLogJpaRepository notificationLogJpaRepository;
    
    @Override
    public NotificationLogEntity saveNotificationLog(String userId, String missionId, String notificationType, String message) {
        NotificationLogEntity entity = NotificationLogEntity.builder()
                .userId(userId)
                .missionId(missionId)
                .notificationType(notificationType)
                .message(message)
                .scheduledAt(LocalDateTime.now())
                .sentAt(LocalDateTime.now())
                .deliveryStatus("SENT")
                .build();
        
        NotificationLogEntity savedEntity = notificationLogJpaRepository.save(entity);
        
        log.info("알림 로그 저장 완료: userId={}, notificationType={}", userId, notificationType);
        return savedEntity;
    }
    
    @Override
    public List<NotificationLogEntity> findRecentNotificationLogs(String userId, int limit) {
        PageRequest pageRequest = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "sentAt"));
        return notificationLogJpaRepository.findByUserIdOrderBySentAtDesc(userId, pageRequest);
    }
    
    @Override
    public List<NotificationLogEntity> findNotificationLogsByBatchId(String batchId) {
        return notificationLogJpaRepository.findByBatchId(batchId);
    }
}
