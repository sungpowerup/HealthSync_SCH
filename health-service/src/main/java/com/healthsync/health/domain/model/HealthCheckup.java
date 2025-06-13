package com.healthsync.health.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 건강검진 도메인 모델 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HealthCheckup {
    
    private String userId;
    private LocalDate checkupDate;
    private int height;
    private int weight;
    private int waistCircumference;
    private double bmi;
    private String vision;
    private String hearing;
    private String bloodPressure;
    private String bloodTestResults;
    
    /**
     * BMI를 계산합니다.
     * 
     * @return BMI 값
     */
    public double calculateBmi() {
        if (height > 0 && weight > 0) {
            double heightInMeters = height / 100.0;
            return weight / (heightInMeters * heightInMeters);
        }
        return 0.0;
    }
    
    /**
     * BMI 상태를 판정합니다.
     * 
     * @return BMI 상태 (저체중, 정상, 과체중, 비만)
     */
    public String getBmiStatus() {
        double bmi = calculateBmi();
        if (bmi < 18.5) {
            return "저체중";
        } else if (bmi < 24.9) {
            return "정상";
        } else if (bmi < 29.9) {
            return "과체중";
        } else {
            return "비만";
        }
    }
}
