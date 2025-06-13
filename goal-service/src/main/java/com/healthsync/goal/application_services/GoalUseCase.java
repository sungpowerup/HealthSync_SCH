package com.healthsync.goal.application_services;

import com.healthsync.goal.domain.services.GoalDomainService;
import com.healthsync.goal.domain.repositories.GoalRepository;
import com.healthsync.goal.dto.*;
import com.healthsync.goal.infrastructure.ports.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * 목표 관리 유스케이스입니다.
 * Clean Architecture의 Application Service 계층에 해당합니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GoalUseCase {
    
    private final GoalDomainService goalDomainService;
    private final GoalRepository goalRepository;
    private final UserServicePort userServicePort;
    private final IntelligenceServicePort intelligenceServicePort;
    private final CachePort cachePort;
    private final EventPublisherPort eventPublisherPort;
    
    /**
     * 미션을 선택하고 목표를 설정합니다.
     * 
     * @param request 미션 선택 요청
     * @return 목표 설정 결과
     */
    public GoalSetupResponse selectMissions(MissionSelectionRequest request) {
        log.info("미션 선택 처리 시작: userId={}", request.getUserId());
        
        // 사용자 정보 검증
        userServicePort.validateUserExists(request.getUserId());
        
        // 미션 선택 검증
        goalDomainService.validateMissionSelection(request);
        
        // 기존 활성 미션 비활성화
        goalRepository.deactivateCurrentMissions(request.getUserId());
        
        // 새 미션 설정 저장
        String goalId = goalRepository.saveGoalSettings(request);
        
        // 선택된 미션 정보 구성
        List<SelectedMission> selectedMissions = request.getSelectedMissionIds().stream()
                .map(missionId -> SelectedMission.builder()
                        .missionId(missionId)
                        .title(getMissionTitle(missionId))
                        .description(getMissionDescription(missionId))
                        .startDate(LocalDate.now().toString())
                        .build())
                .toList();
        
        // 이벤트 발행
        eventPublisherPort.publishGoalSetEvent(request.getUserId(), request.getSelectedMissionIds());
        
        // 캐시 무효화
        cachePort.invalidateUserMissionCache(request.getUserId());
        
        GoalSetupResponse response = GoalSetupResponse.builder()
                .goalId(goalId)
                .selectedMissions(selectedMissions)
                .message("선택하신 미션으로 건강 목표가 설정되었습니다.")
                .setupCompletedAt(java.time.LocalDateTime.now().toString())
                .build();
        
        log.info("미션 선택 처리 완료: userId={}, goalId={}", request.getUserId(), goalId);
        return response;
    }
    
    /**
     * 설정된 활성 미션을 조회합니다.
     * 
     * @param userId 사용자 ID
     * @return 활성 미션 목록
     */
    @Transactional(readOnly = true)
    public ActiveMissionsResponse getActiveMissions(String userId) {
        log.info("활성 미션 조회: userId={}", userId);
        
        // 캐시 확인
        ActiveMissionsResponse cachedResponse = cachePort.getActiveMissions(userId);
        if (cachedResponse != null) {
            log.info("캐시에서 활성 미션 조회: userId={}", userId);
            return cachedResponse;
        }
        
        // DB에서 활성 미션 조회
        List<DailyMission> dailyMissions = goalRepository.findActiveMissionsByUserId(userId);
        
        // 완료률 계산
        int totalMissions = dailyMissions.size();
        int todayCompletedCount = (int) dailyMissions.stream()
                .filter(DailyMission::isCompletedToday)
                .count();
        double completionRate = totalMissions > 0 ? (double) todayCompletedCount / totalMissions * 100 : 0;
        
        ActiveMissionsResponse response = ActiveMissionsResponse.builder()
                .dailyMissions(dailyMissions)
                .totalMissions(totalMissions)
                .todayCompletedCount(todayCompletedCount)
                .completionRate(completionRate)
                .build();
        
        // 캐시 저장
        cachePort.cacheActiveMissions(userId, response);
        
        log.info("활성 미션 조회 완료: userId={}, totalMissions={}", userId, totalMissions);
        return response;
    }
    
    /**
     * 미션 완료를 처리합니다.
     * 
     * @param missionId 미션 ID
     * @param request 미션 완료 요청
     * @return 미션 완료 결과
     */
    public MissionCompleteResponse completeMission(String missionId, MissionCompleteRequest request) {
        log.info("미션 완료 처리: userId={}, missionId={}", request.getUserId(), missionId);
        
        // 미션 완료 검증
        goalDomainService.validateMissionCompletion(missionId, request.getUserId());
        
        // 미션 완료 기록
        goalRepository.recordMissionCompletion(missionId, request);
        
        // 연속 달성 일수 계산
        int newStreakDays = goalDomainService.calculateStreakDays(request.getUserId(), missionId);
        
        // 총 완료 횟수 조회
        int totalCompletedCount = goalRepository.getTotalCompletedCount(request.getUserId(), missionId);
        
        // 축하 메시지 요청 (5일 이상 연속 달성 시)
        String achievementMessage = "";
        if (newStreakDays >= 5) {
            try {
                CelebrationResponse celebration = intelligenceServicePort
                        .requestCelebrationMessage(request.getUserId(), missionId, newStreakDays);
                achievementMessage = celebration.getCongratsMessage();
            } catch (Exception e) {
                log.warn("축하 메시지 생성 실패: {}", e.getMessage());
                achievementMessage = String.format("🎉 %d일 연속 달성! 훌륭합니다!", newStreakDays);
            }
        }
        
        // 이벤트 발행
        eventPublisherPort.publishMissionCompleteEvent(request.getUserId(), missionId, 
                CompletionData.builder()
                        .userId(request.getUserId())
                        .missionId(missionId)
                        .completed(request.isCompleted())
                        .completedAt(request.getCompletedAt())
                        .notes(request.getNotes())
                        .build());
        
        // 캐시 무효화
        cachePort.invalidateUserMissionCache(request.getUserId());
        
        MissionCompleteResponse response = MissionCompleteResponse.builder()
                .message("미션이 성공적으로 완료되었습니다.")
                .status("SUCCESS")
                .achievementMessage(achievementMessage)
                .newStreakDays(newStreakDays)
                .totalCompletedCount(totalCompletedCount)
                .earnedPoints(calculateEarnedPoints(newStreakDays))
                .build();
        
        log.info("미션 완료 처리 완료: userId={}, missionId={}, streakDays={}", 
                request.getUserId(), missionId, newStreakDays);
        return response;
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
    @Transactional(readOnly = true)
    public MissionHistoryResponse getMissionHistory(String userId, String startDate, String endDate, String missionIds) {
        log.info("미션 이력 조회: userId={}, period={} to {}", userId, startDate, endDate);
        
        // 캐시 키 생성
        String cacheKey = String.format("mission_history:%s:%s:%s:%s", userId, startDate, endDate, missionIds);
        
        // 캐시 확인
        MissionHistoryResponse cachedResponse = cachePort.getMissionHistory(cacheKey);
        if (cachedResponse != null) {
            log.info("캐시에서 미션 이력 조회: userId={}", userId);
            return cachedResponse;
        }
        
        // 기본값 설정
        if (startDate == null) startDate = LocalDate.now().minusMonths(1).toString();
        if (endDate == null) endDate = LocalDate.now().toString();
        
        // 미션 이력 조회
        List<MissionStats> missionStats = goalRepository.findMissionHistoryByPeriod(userId, startDate, endDate, missionIds);
        
        // 통계 계산
        AchievementStats achievementStats = goalDomainService.calculateAchievementStats(userId, startDate, endDate);
        
        // 차트 데이터 생성
        Object chartData = goalDomainService.generateChartData(missionStats);
        
        // 인사이트 생성
        List<String> insights = goalDomainService.analyzeProgressPatterns(missionStats);
        
        MissionHistoryResponse response = MissionHistoryResponse.builder()
                .totalAchievementRate(achievementStats.getTotalAchievementRate())
                .periodAchievementRate(achievementStats.getPeriodAchievementRate())
                .bestStreak(achievementStats.getBestStreak())
                .missionStats(missionStats)
                .chartData(chartData)
                .period(Period.builder()
                        .startDate(startDate)
                        .endDate(endDate)
                        .build())
                .insights(insights)
                .build();
        
        // 캐시 저장
        cachePort.cacheMissionHistory(cacheKey, response);
        
        log.info("미션 이력 조회 완료: userId={}, achievementRate={}", userId, response.getTotalAchievementRate());
        return response;
    }
    
    /**
     * 미션을 재설정합니다.
     * 
     * @param request 미션 재설정 요청
     * @return 미션 재설정 결과
     */
    public MissionResetResponse resetMissions(MissionResetRequest request) {
        log.info("미션 재설정 처리: userId={}, reason={}", request.getUserId(), request.getReason());
        
        // 현재 미션 비활성화
        goalRepository.deactivateCurrentMissions(request.getUserId());
        
        // 새로운 미션 추천 요청
        List<RecommendedMission> newRecommendations;
        try {
            List<Mission> aiRecommendations = intelligenceServicePort
                    .requestNewMissionRecommendations(request.getUserId(), request.getReason());
            
            newRecommendations = aiRecommendations.stream()
                    .map(mission -> RecommendedMission.builder()
                            .missionId(mission.getMissionId())
                            .title(mission.getTitle())
                            .description(mission.getDescription())
                            .category(mission.getCategory())
                            .build())
                    .toList();
        } catch (Exception e) {
            log.warn("AI 미션 추천 실패, 기본 추천 사용: {}", e.getMessage());
            newRecommendations = getDefaultRecommendations();
        }
        
        // 이벤트 발행
        eventPublisherPort.publishMissionResetEvent(request.getUserId(), request.getReason());
        
        // 캐시 무효화
        cachePort.invalidateUserMissionCache(request.getUserId());
        
        MissionResetResponse response = MissionResetResponse.builder()
                .message("미션이 성공적으로 재설정되었습니다.")
                .newRecommendations(newRecommendations)
                .resetCompletedAt(java.time.LocalDateTime.now().toString())
                .build();
        
        log.info("미션 재설정 완료: userId={}, newRecommendationCount={}", 
                request.getUserId(), newRecommendations.size());
        return response;
    }
    
    /**
     * 미션 제목을 반환합니다.
     * 
     * @param missionId 미션 ID
     * @return 미션 제목
     */
    private String getMissionTitle(String missionId) {
        // 실제 구현에서는 미션 마스터 데이터에서 조회
        return switch (missionId) {
            case "mission_1" -> "하루 8잔 물마시기";
            case "mission_2" -> "점심시간 10분 산책";
            case "mission_3" -> "어깨 스트레칭 3세트";
            case "mission_4" -> "계단 이용하기";
            case "mission_5" -> "금연/금주 도전";
            default -> "건강 미션 " + missionId;
        };
    }
    
    /**
     * 미션 설명을 반환합니다.
     * 
     * @param missionId 미션 ID
     * @return 미션 설명
     */
    private String getMissionDescription(String missionId) {
        // 실제 구현에서는 미션 마스터 데이터에서 조회
        return switch (missionId) {
            case "mission_1" -> "하루 종일 충분한 수분 섭취로 신진대사를 활발하게 유지하세요.";
            case "mission_2" -> "점심식사 후 가벼운 산책으로 소화를 돕고 스트레스를 해소하세요.";
            case "mission_3" -> "장시간 앉아있는 자세로 인한 어깨 결림을 스트레칭으로 완화하세요.";
            case "mission_4" -> "엘리베이터 대신 계단을 이용하여 하체 근력을 강화하세요.";
            case "mission_5" -> "건강한 생활습관을 위해 금연과 절주에 도전해보세요.";
            default -> "건강한 생활습관을 위한 미션입니다.";
        };
    }
    
    /**
     * 획득 포인트를 계산합니다.
     * 
     * @param streakDays 연속 달성 일수
     * @return 획득 포인트
     */
    private int calculateEarnedPoints(int streakDays) {
        int basePoints = 10;
        int bonusPoints = streakDays >= 7 ? 20 : streakDays >= 3 ? 10 : 0;
        return basePoints + bonusPoints;
    }
    
    /**
     * 기본 미션 추천 목록을 반환합니다.
     * 
     * @return 기본 추천 미션 목록
     */
    private List<RecommendedMission> getDefaultRecommendations() {
        return List.of(
                RecommendedMission.builder()
                        .missionId("mission_1")
                        .title("하루 8잔 물마시기")
                        .description("충분한 수분 섭취로 신진대사 활성화")
                        .category("생활습관")
                        .build(),
                RecommendedMission.builder()
                        .missionId("mission_2")
                        .title("점심시간 10분 산책")
                        .description("가벼운 산책으로 스트레스 해소")
                        .category("운동")
                        .build(),
                RecommendedMission.builder()
                        .missionId("mission_3")
                        .title("어깨 스트레칭 3세트")
                        .description("어깨 결림 완화 스트레칭")
                        .category("운동")
                        .build()
        );
    }
}
