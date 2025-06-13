package com.healthsync.goal.interface_adapters.controllers;

import com.healthsync.common.dto.ApiResponse;
import com.healthsync.goal.application_services.GoalUseCase;
import com.healthsync.goal.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * 목표 관리 관련 API를 제공하는 컨트롤러입니다.
 * Clean Architecture의 Interface Adapter 계층에 해당합니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/goals")
@RequiredArgsConstructor
@Tag(name = "목표 관리", description = "건강 목표 설정 및 미션 관리 API")
public class GoalController {
    
    private final GoalUseCase goalUseCase;
    
    /**
     * 미션을 선택하고 목표를 설정합니다.
     * 
     * @param request 미션 선택 요청
     * @return 목표 설정 결과
     */
    @PostMapping("/missions/select")
    @Operation(summary = "미션 선택 및 목표 설정", description = "사용자가 선택한 미션으로 건강 목표를 설정합니다")
    public ResponseEntity<ApiResponse<GoalSetupResponse>> selectMissions(@Valid @RequestBody MissionSelectionRequest request) {
        log.info("미션 선택 및 목표 설정 요청: userId={}, missionCount={}", request.getUserId(), request.getSelectedMissionIds().size());
        
        GoalSetupResponse response = goalUseCase.selectMissions(request);
        
        log.info("미션 선택 및 목표 설정 완료: userId={}, goalId={}", request.getUserId(), response.getGoalId());
        return ResponseEntity.ok(ApiResponse.success("목표 설정이 완료되었습니다.", response));
    }
    
    /**
     * 설정된 활성 미션을 조회합니다.
     * 
     * @param userId 사용자 ID
     * @return 활성 미션 목록
     */
    @GetMapping("/missions/active")
    @Operation(summary = "설정된 목표 조회", description = "사용자의 현재 활성 미션과 진행 상황을 조회합니다")
    public ResponseEntity<ApiResponse<ActiveMissionsResponse>> getActiveMissions(@RequestParam String userId) {
        log.info("활성 미션 조회 요청: userId={}", userId);
        
        ActiveMissionsResponse response = goalUseCase.getActiveMissions(userId);
        
        log.info("활성 미션 조회 완료: userId={}, totalMissions={}", userId, response.getTotalMissions());
        return ResponseEntity.ok(ApiResponse.success("활성 미션 조회가 완료되었습니다.", response));
    }
    
    /**
     * 미션 완료를 처리합니다.
     * 
     * @param missionId 미션 ID
     * @param request 미션 완료 요청
     * @return 미션 완료 결과
     */
    @PutMapping("/missions/{missionId}/complete")
    @Operation(summary = "미션 완료 처리", description = "사용자의 미션 완료를 기록하고 성과를 업데이트합니다")
    public ResponseEntity<ApiResponse<MissionCompleteResponse>> completeMission(
            @PathVariable String missionId,
            @Valid @RequestBody MissionCompleteRequest request) {
        log.info("미션 완료 처리 요청: userId={}, missionId={}", request.getUserId(), missionId);
        
        MissionCompleteResponse response = goalUseCase.completeMission(missionId, request);
        
        log.info("미션 완료 처리 완료: userId={}, missionId={}, streakDays={}", 
                request.getUserId(), missionId, response.getNewStreakDays());
        return ResponseEntity.ok(ApiResponse.success("미션 완료가 기록되었습니다.", response));
    }
    
    /**
     * 미션 달성 이력을 조회합니다.
     * 
     * @param userId 사용자 ID
     * @param startDate 시작일
     * @param endDate 종료일
     * @param missionIds 미션 ID 목록
     * @return 미션 달성 이력
     */
    @GetMapping("/missions/history")
    @Operation(summary = "미션 달성 이력 조회", description = "지정한 기간의 미션 달성 이력과 통계를 조회합니다")
    public ResponseEntity<ApiResponse<MissionHistoryResponse>> getMissionHistory(
            @RequestParam String userId,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String missionIds) {
        log.info("미션 이력 조회 요청: userId={}, period={} to {}", userId, startDate, endDate);
        
        MissionHistoryResponse response = goalUseCase.getMissionHistory(userId, startDate, endDate, missionIds);
        
        log.info("미션 이력 조회 완료: userId={}, achievementRate={}", userId, response.getTotalAchievementRate());
        return ResponseEntity.ok(ApiResponse.success("미션 이력 조회가 완료되었습니다.", response));
    }
    
    /**
     * 미션을 재설정합니다.
     * 
     * @param request 미션 재설정 요청
     * @return 미션 재설정 결과
     */
    @PostMapping("/missions/reset")
    @Operation(summary = "목표 재설정", description = "현재 미션을 중단하고 새로운 미션으로 재설정합니다")
    public ResponseEntity<ApiResponse<MissionResetResponse>> resetMissions(@Valid @RequestBody MissionResetRequest request) {
        log.info("미션 재설정 요청: userId={}, reason={}", request.getUserId(), request.getReason());
        
        MissionResetResponse response = goalUseCase.resetMissions(request);
        
        log.info("미션 재설정 완료: userId={}, newRecommendationCount={}", 
                request.getUserId(), response.getNewRecommendations().size());
        return ResponseEntity.ok(ApiResponse.success("미션 재설정이 완료되었습니다.", response));
    }
}
