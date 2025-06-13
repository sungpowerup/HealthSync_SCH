package com.healthsync.goal.infrastructure.entities;

import com.healthsync.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 사용자 미션을 저장하는 엔티티 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Entity
@Table(name = "user_missions")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserMissionEntity extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @Column(name = "mission_id", nullable = false)
    private String missionId;
    
    @Column(name = "title", nullable = false)
    private String title;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "category")
    private String category;
    
    @Column(name = "difficulty")
    private String difficulty;
    
    @Column(name = "active", nullable = false)
    @Builder.Default
    private Boolean active = true;
    
    @Column(name = "streak_days")
    @Builder.Default
    private Integer streakDays = 0;
    
    @Column(name = "total_completed_count")
    @Builder.Default
    private Integer totalCompletedCount = 0;
    
    /**
     * 완료 횟수를 증가시킵니다.
     */
    public void incrementCompletedCount() {
        this.totalCompletedCount++;
    }
    
    /**
     * 연속 달성 일수를 업데이트합니다.
     * 
     * @param days 연속 달성 일수
     */
    public void updateStreakDays(int days) {
        this.streakDays = days;
    }
    
    /**
     * 미션을 비활성화합니다.
     */
    public void deactivate() {
        this.active = false;
    }
}
