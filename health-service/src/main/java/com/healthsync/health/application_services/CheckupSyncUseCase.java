package com.healthsync.health.application_services;

import com.healthsync.health.domain.services.CheckupDataDomainService;
import com.healthsync.health.dto.HealthSyncResponse;
import com.healthsync.health.interface_adapters.adapters.UserServiceAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 건강검진 데이터 동기화 유스케이스입니다.
 * Clean Architecture의 Application Service 계층에 해당합니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CheckupSyncUseCase {
    
    private final CheckupDataDomainService checkupDataDomainService;
    private final UserServiceAdapter userServiceAdapter;
    
    /**
     * 건강검진 데이터를 동기화합니다.
     * 
     * @param userId 사용자 ID
     * @return 동기화 결과
     */
    public HealthSyncResponse syncHealthData(String userId) {
        log.info("건강검진 데이터 동기화 시작: userId={}", userId);
        
        try {
            // 사용자 정보 확인
            userServiceAdapter.getUserBasicInfo(userId);
            
            // Mock 건강검진 데이터 생성 및 저장
            checkupDataDomainService.syncHealthCheckupData(userId);
            
            return HealthSyncResponse.builder()
                    .syncStatus("SUCCESS")
                    .message("건강검진 데이터가 성공적으로 연동되었습니다.")
                    .isReadyForAnalysis(true)
                    .syncedAt(java.time.LocalDateTime.now().toString())
                    .build();
                    
        } catch (Exception e) {
            log.error("건강검진 데이터 동기화 실패: userId={}, error={}", userId, e.getMessage(), e);
            
            return HealthSyncResponse.builder()
                    .syncStatus("FAILED")
                    .message("건강검진 데이터 연동에 실패했습니다.")
                    .isReadyForAnalysis(false)
                    .syncedAt(java.time.LocalDateTime.now().toString())
                    .build();
        }
    }
}
