package com.healthsync.goal.infrastructure.entities;

import com.healthsync.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 목표 설정을 저장하는 엔티티 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Entity
@Table(name = "goal_settings")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoalSettingEntity extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @Column(name = "goal_name", nullable = false)
    private String goalName;
    
    @Column(name = "goal_description")
    private String goalDescription;
    
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    
    @Column(name = "end_date")
    private LocalDate endDate;
    
    @Column(name = "status", nullable = false)
    private String status;
    
    /**
     * 목표가 활성 상태인지 확인합니다.
     * 
     * @return 활성 상태 여부
     */
    public boolean isActive() {
        return "ACTIVE".equals(status);
    }
    
    /**
     * 목표가 완료된 상태인지 확인합니다.
     * 
     * @return 완료 상태 여부
     */
    public boolean isCompleted() {
        return "COMPLETED".equals(status);
    }
}
