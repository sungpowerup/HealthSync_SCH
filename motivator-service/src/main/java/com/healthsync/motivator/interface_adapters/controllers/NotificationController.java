package com.healthsync.motivator.interface_adapters.controllers;

import com.healthsync.common.dto.ApiResponse;
import com.healthsync.motivator.application_services.MotivationUseCase;
import com.healthsync.motivator.dto.EncouragementRequest;
import com.healthsync.motivator.dto.EncouragementResponse;
import com.healthsync.motivator.dto.BatchNotificationRequest;
import com.healthsync.motivator.dto.BatchNotificationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * 동기부여 알림 관련 API를 제공하는 컨트롤러입니다.
 * Clean Architecture의 Interface Adapter 계층에 해당합니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/motivator")
@RequiredArgsConstructor
@Tag(name = "동기부여 알림", description = "사용자 동기부여 메시지 생성 및 배치 알림 API")
public class NotificationController {
    
    private final MotivationUseCase motivationUseCase;
    
    /**
     * 미션 독려 메시지를 생성합니다.
     * 
     * @param request 독려 요청
     * @return 독려 메시지
     */
    @PostMapping("/notifications/encouragement")
    @Operation(summary = "미션 독려 메시지 생성", description = "사용자의 미션 진행 상황을 바탕으로 개인화된 독려 메시지를 생성합니다")
    public ResponseEntity<ApiResponse<EncouragementResponse>> generateEncouragementMessage(@Valid @RequestBody EncouragementRequest request) {
        log.info("독려 메시지 생성 요청: userId={}, missionCount={}", request.getUserId(), request.getMissionsStatus().size());
        
        EncouragementResponse response = motivationUseCase.generateEncouragementMessage(request);
        
        log.info("독려 메시지 생성 완료: userId={}, motivationType={}", request.getUserId(), response.getMotivationType());
        return ResponseEntity.ok(ApiResponse.success("독려 메시지가 생성되었습니다.", response));
    }
    
    /**
     * 배치 알림을 처리합니다.
     * 
     * @param request 배치 알림 요청
     * @return 배치 처리 결과
     */
    @PostMapping("/batch/notifications")
    @Operation(summary = "주기적 AI 알림 트리거", description = "전체 사용자를 대상으로 배치 형태의 동기부여 알림을 처리합니다")
    public ResponseEntity<ApiResponse<BatchNotificationResponse>> processBatchNotifications(@Valid @RequestBody BatchNotificationRequest request) {
        log.info("배치 알림 처리 요청: triggerTime={}, targetUsers={}, type={}", 
                request.getTriggerTime(), request.getTargetUsers().size(), request.getNotificationType());
        
        BatchNotificationResponse response = motivationUseCase.processBatchNotifications(request);
        
        log.info("배치 알림 처리 완료: processedCount={}, successCount={}", 
                response.getProcessedCount(), response.getSuccessCount());
        return ResponseEntity.ok(ApiResponse.success("배치 알림 처리가 완료되었습니다.", response));
    }
}
