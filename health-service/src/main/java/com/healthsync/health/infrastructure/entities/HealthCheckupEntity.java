package com.healthsync.health.infrastructure.entities;

import com.healthsync.common.entity.BaseEntity;
import com.healthsync.health.domain.model.HealthCheckup;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 건강검진 데이터를 저장하는 엔티티 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Entity
@Table(name = "health_checkups")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HealthCheckupEntity extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_id", nullable = false)
    private String userId;
    
    @Column(name = "checkup_date", nullable = false)
    private LocalDate checkupDate;
    
    @Column(name = "height")
    private Integer height;
    
    @Column(name = "weight")
    private Integer weight;
    
    @Column(name = "waist_circumference")
    private Integer waistCircumference;
    
    @Column(name = "bmi")
    private Double bmi;
    
    @Column(name = "vision")
    private String vision;
    
    @Column(name = "hearing")
    private String hearing;
    
    @Column(name = "blood_pressure")
    private String bloodPressure;
    
    @Column(name = "blood_test_results", columnDefinition = "TEXT")
    private String bloodTestResults;
    
    /**
     * 도메인 모델로부터 엔티티를 생성합니다.
     * 
     * @param healthCheckup 건강검진 도메인 모델
     * @return 건강검진 엔티티
     */
    public static HealthCheckupEntity fromDomain(HealthCheckup healthCheckup) {
        return HealthCheckupEntity.builder()
                .userId(healthCheckup.getUserId())
                .checkupDate(healthCheckup.getCheckupDate())
                .height(healthCheckup.getHeight())
                .weight(healthCheckup.getWeight())
                .waistCircumference(healthCheckup.getWaistCircumference())
                .bmi(healthCheckup.getBmi())
                .vision(healthCheckup.getVision())
                .hearing(healthCheckup.getHearing())
                .bloodPressure(healthCheckup.getBloodPressure())
                .bloodTestResults(healthCheckup.getBloodTestResults())
                .build();
    }
    
    /**
     * 엔티티를 도메인 모델로 변환합니다.
     * 
     * @return 건강검진 도메인 모델
     */
    public HealthCheckup toDomain() {
        return HealthCheckup.builder()
                .userId(this.userId)
                .checkupDate(this.checkupDate)
                .height(this.height)
                .weight(this.weight)
                .waistCircumference(this.waistCircumference)
                .bmi(this.bmi)
                .vision(this.vision)
                .hearing(this.hearing)
                .bloodPressure(this.bloodPressure)
                .bloodTestResults(this.bloodTestResults)
                .build();
    }
}
