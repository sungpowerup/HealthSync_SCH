package com.healthsync.motivator.domain.repositories;

import com.healthsync.motivator.infrastructure.entities.NotificationLogEntity;

import java.util.List;

/**
 * 알림 데이터 저장소 인터페이스입니다.
 * Clean Architecture의 Domain 계층에서 정의합니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
public interface NotificationRepository {
    
    /**
     * 알림 로그를 저장합니다.
     * 
     * @param userId 사용자 ID
     * @param missionId 미션 ID
     * @param notificationType 알림 유형
     * @param message 메시지
     * @return 저장된 알림 로그 엔티티
     */
    NotificationLogEntity saveNotificationLog(String userId, String missionId, String notificationType, String message);
    
    /**
     * 사용자의 최근 알림 로그를 조회합니다.
     * 
     * @param userId 사용자 ID
     * @param limit 조회할 최대 수
     * @return 알림 로그 목록
     */
    List<NotificationLogEntity> findRecentNotificationLogs(String userId, int limit);
    
    /**
     * 배치 알림 로그를 조회합니다.
     * 
     * @param batchId 배치 ID
     * @return 알림 로그 목록
     */
    List<NotificationLogEntity> findNotificationLogsByBatchId(String batchId);
}
