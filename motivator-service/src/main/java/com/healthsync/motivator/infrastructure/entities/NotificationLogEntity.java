package com.healthsync.motivator.infrastructure.entities;

import com.healthsync.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 알림 로그를 저장하는 엔티티 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Entity
@Table(name = "notification_logs")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationLogEntity extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_id", nullable = false)
    private String userId;
    
    @Column(name = "mission_id")
    private String missionId;
    
    @Column(name = "notification_type", nullable = false)
    private String notificationType;
    
    @Column(name = "message", nullable = false, columnDefinition = "TEXT")
    private String message;
    
    @Column(name = "delivery_channel")
    private String deliveryChannel;
    
    @Column(name = "scheduled_at")
    private LocalDateTime scheduledAt;
    
    @Column(name = "sent_at")
    private LocalDateTime sentAt;
    
    @Column(name = "delivery_status")
    private String deliveryStatus;
    
    @Column(name = "response_action")
    private String responseAction;
    
    @Column(name = "response_time")
    private LocalDateTime responseTime;
    
    @Column(name = "effectiveness")
    private Double effectiveness;
    
    @Column(name = "batch_id")
    private String batchId;
    
    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;
    
    /**
     * 전송 완료 여부를 확인합니다.
     * 
     * @return 전송 완료 여부
     */
    public boolean isDelivered() {
        return "SENT".equals(deliveryStatus) || "DELIVERED".equals(deliveryStatus);
    }
    
    /**
     * 전송 완료로 마크합니다.
     */
    public void markAsDelivered() {
        this.deliveryStatus = "DELIVERED";
        this.sentAt = LocalDateTime.now();
    }
    
    /**
     * 사용자 응답을 기록합니다.
     * 
     * @param action 응답 액션
     */
    public void recordResponse(String action) {
        this.responseAction = action;
        this.responseTime = LocalDateTime.now();
    }
    
    /**
     * 효과성 점수를 업데이트합니다.
     * 
     * @param score 효과성 점수
     */
    public void updateEffectiveness(double score) {
        this.effectiveness = score;
    }
}
