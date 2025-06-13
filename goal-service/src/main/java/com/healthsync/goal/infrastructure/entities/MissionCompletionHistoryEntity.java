package com.healthsync.goal.infrastructure.entities;

import com.healthsync.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 미션 완료 기록을 저장하는 엔티티 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Entity
@Table(name = "mission_completion_history")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MissionCompletionHistoryEntity extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @Column(name = "mission_id", nullable = false)
    private String missionId;
    
    @Column(name = "completed_date", nullable = false)
    private LocalDate completedDate;
    
    @Column(name = "completed_at", nullable = false)
    private LocalDateTime completedAt;
    
    @Column(name = "notes")
    private String notes;
    
    @Column(name = "streak_days_at_completion")
    private Integer streakDaysAtCompletion;
    
    /**
     * 오늘 완료된 미션인지 확인합니다.
     * 
     * @return 오늘 완료 여부
     */
    public boolean isCompletedToday() {
        return LocalDate.now().equals(completedDate);
    }
}
