package com.healthsync.health.domain.services;

import com.healthsync.health.domain.model.HealthCheckup;
import com.healthsync.health.domain.repositories.HealthRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * 건강검진 데이터 처리를 담당하는 도메인 서비스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CheckupDataDomainService {
    
    private final HealthRepository healthRepository;
    
    /**
     * 건강검진 데이터를 동기화합니다.
     * 
     * @param userId 사용자 ID
     */
    public void syncHealthCheckupData(String userId) {
        log.info("건강검진 데이터 동기화 처리: userId={}", userId);
        
        // Mock 건강검진 데이터 생성
        HealthCheckup healthCheckup = HealthCheckup.builder()
                .userId(userId)
                .checkupDate(LocalDate.now().minusMonths(3))
                .height(175)
                .weight(72)
                .waistCircumference(80)
                .bmi(23.5)
                .vision("1.0/1.0")
                .hearing("정상")
                .bloodPressure("130/85")
                .bloodTestResults("정상")
                .build();
        
        // 건강검진 데이터 저장
        healthRepository.saveHealthCheckup(healthCheckup);
        
        log.info("건강검진 데이터 저장 완료: userId={}", userId);
    }
}
