package com.healthsync.health.application_services;

import com.healthsync.health.domain.services.HealthAnalysisDomainService;
import com.healthsync.health.domain.repositories.HealthRepository;
import com.healthsync.health.dto.HealthHistoryResponse;
import com.healthsync.health.dto.UserInfo;
import com.healthsync.health.dto.CheckupRecord;
import com.healthsync.health.interface_adapters.adapters.CacheAdapter;
import com.healthsync.health.interface_adapters.adapters.UserServiceAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

/**
 * 건강검진 데이터 조회 유스케이스입니다.
 * Clean Architecture의 Application Service 계층에 해당합니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CheckupQueryUseCase {
    
    private final HealthRepository healthRepository;
    private final HealthAnalysisDomainService healthAnalysisDomainService;
    private final UserServiceAdapter userServiceAdapter;
    private final CacheAdapter cacheAdapter;
    
    /**
     * 건강검진 이력을 조회합니다.
     * 
     * @param userId 사용자 ID
     * @param maxRecords 최대 조회 건수
     * @return 건강검진 이력
     */
    public HealthHistoryResponse getHealthHistory(String userId, int maxRecords) {
        log.info("건강검진 이력 조회: userId={}, maxRecords={}", userId, maxRecords);
        
        // 캐시 확인
        String cacheKey = "health_history:" + userId;
        HealthHistoryResponse cachedResponse = cacheAdapter.getHealthHistory(cacheKey);
        if (cachedResponse != null) {
            log.info("캐시에서 건강검진 이력 조회: userId={}", userId);
            return cachedResponse;
        }
        
        // 사용자 정보 조회
        UserInfo userInfo = userServiceAdapter.getUserBasicInfo(userId);
        
        // Mock 건강검진 데이터 생성
        List<CheckupRecord> checkupRecords = generateMockCheckupRecords(maxRecords);
        
        // 차트 데이터 생성
        Object chartData = healthAnalysisDomainService.generateChartData(checkupRecords);
        
        HealthHistoryResponse response = HealthHistoryResponse.builder()
                .userInfo(userInfo)
                .checkupRecords(checkupRecords)
                .chartData(chartData)
                .normalRangeReference("정상 범위 기준: BMI 18.5-24.9, 혈압 90-119/60-79")
                .build();
        
        // 캐시 저장
        cacheAdapter.cacheHealthHistory(cacheKey, response);
        
        return response;
    }
    
    /**
     * Mock 건강검진 데이터를 생성합니다.
     * 
     * @param maxRecords 생성할 레코드 수
     * @return 건강검진 기록 목록
     */
    private List<CheckupRecord> generateMockCheckupRecords(int maxRecords) {
        return IntStream.range(0, Math.min(maxRecords, 5))
                .mapToObj(i -> CheckupRecord.builder()
                        .year(2024 - i)
                        .month(3)
                        .height(175)
                        .weight(72 + i)
                        .waist(80 + i)
                        .bmi(23.5 + (i * 0.3))
                        .vision("1.0/1.0")
                        .hearing("정상")
                        .bloodPressure((130 + i*5) + "/" + (85 + i*2))
                        .bloodTest("정상")
                        .build())
                .toList();
    }
}
