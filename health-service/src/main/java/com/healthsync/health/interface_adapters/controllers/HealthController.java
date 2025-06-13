package com.healthsync.health.interface_adapters.controllers;

import com.healthsync.common.dto.ApiResponse;
import com.healthsync.health.application_services.CheckupSyncUseCase;
import com.healthsync.health.application_services.CheckupQueryUseCase;
import com.healthsync.health.dto.HealthSyncRequest;
import com.healthsync.health.dto.HealthSyncResponse;
import com.healthsync.health.dto.HealthHistoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * 건강검진 관련 API를 제공하는 컨트롤러입니다.
 * Clean Architecture의 Interface Adapter 계층에 해당합니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/health")
@RequiredArgsConstructor
@Tag(name = "건강 관리", description = "건강검진 데이터 관련 API")
public class HealthController {
    
    private final CheckupSyncUseCase checkupSyncUseCase;
    private final CheckupQueryUseCase checkupQueryUseCase;
    
    /**
     * 건강검진 결과를 연동합니다.
     * 
     * @param request 건강검진 연동 요청
     * @return 연동 결과
     */
    @PostMapping("/checkup/sync")
    @Operation(summary = "건강검진 결과 연동", description = "건강보험공단 데이터와 연동하여 건강검진 결과를 가져옵니다")
    public ResponseEntity<ApiResponse<HealthSyncResponse>> syncHealthCheckup(@Valid @RequestBody HealthSyncRequest request) {
        log.info("건강검진 데이터 연동 요청: userId={}", request.getUserId());
        
        HealthSyncResponse response = checkupSyncUseCase.syncHealthData(request.getUserId());
        
        log.info("건강검진 데이터 연동 완료: userId={}, status={}", request.getUserId(), response.getSyncStatus());
        return ResponseEntity.ok(ApiResponse.success("건강검진 데이터 연동이 완료되었습니다.", response));
    }
    
    /**
     * 건강검진 이력을 조회합니다.
     * 
     * @param userId 사용자 ID
     * @param maxRecords 최대 조회 건수
     * @return 건강검진 이력
     */
    @GetMapping("/checkup/history")
    @Operation(summary = "건강검진 이력 조회", description = "사용자의 건강검진 이력을 조회합니다")
    public ResponseEntity<ApiResponse<HealthHistoryResponse>> getHealthHistory(
            @RequestParam String userId,
            @RequestParam(defaultValue = "5") int maxRecords) {
        log.info("건강검진 이력 조회 요청: userId={}, maxRecords={}", userId, maxRecords);
        
        HealthHistoryResponse response = checkupQueryUseCase.getHealthHistory(userId, maxRecords);
        
        log.info("건강검진 이력 조회 완료: userId={}, recordCount={}", userId, response.getCheckupRecords().size());
        return ResponseEntity.ok(ApiResponse.success("건강검진 이력 조회가 완료되었습니다.", response));
    }
}
