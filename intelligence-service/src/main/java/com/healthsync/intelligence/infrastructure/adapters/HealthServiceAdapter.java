package com.healthsync.intelligence.infrastructure.adapters;

import com.healthsync.common.exception.ExternalApiException;
import com.healthsync.intelligence.dto.HealthData;
import com.healthsync.intelligence.infrastructure.ports.HealthServicePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Health Service와의 통신을 담당하는 어댑터 클래스입니다.
 * Clean Architecture의 Infrastructure 계층에 해당합니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class HealthServiceAdapter implements HealthServicePort {
    
    private final WebClient webClient = WebClient.builder().build();
    
    @Value("${services.health-service.url}")
    private String healthServiceUrl;
    
    @Override
    public HealthData getHealthData(String userId) {
        try {
            log.info("Health Service 건강 데이터 조회: userId={}", userId);
            
            // 실제 구현에서는 Health Service API 호출
            // Mock 데이터 반환
            return HealthData.builder()
                    .bmi(24.2)
                    .bloodPressure("140/90")
                    .cholesterol("220 mg/dL")
                    .bloodSugar("95 mg/dL")
                    .checkupDate("2024-03-15")
                    .build();
                    
        } catch (Exception e) {
            log.error("Health Service 호출 실패: userId={}, error={}", userId, e.getMessage(), e);
            throw new ExternalApiException("건강 데이터 조회에 실패했습니다.");
        }
    }
}
