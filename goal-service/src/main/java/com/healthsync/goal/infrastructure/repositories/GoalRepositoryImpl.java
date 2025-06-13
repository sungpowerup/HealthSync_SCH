package com.healthsync.goal.infrastructure.repositories;

import com.healthsync.goal.domain.repositories.GoalRepository;
import com.healthsync.goal.dto.*;
import com.healthsync.goal.infrastructure.entities.GoalSettingEntity;
import com.healthsync.goal.infrastructure.entities.UserMissionEntity;
import com.healthsync.goal.infrastructure.entities.MissionCompletionHistoryEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

/**
 * 목표 데이터 저장소 구현체입니다.
 * Clean Architecture의 Infrastructure 계층에 해당합니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class GoalRepositoryImpl implements GoalRepository {
    
    private final GoalJpaRepository goalJpaRepository;
    private final UserMissionJpaRepository userMissionJpaRepository;
    private final MissionCompletionJpaRepository missionCompletionJpaRepository;
    
    @Override
    public String saveGoalSettings(MissionSelectionRequest request) {
        // 목표 설정 저장
        GoalSettingEntity goalSetting = GoalSettingEntity.builder()
                .userId(Long.parseLong(request.getUserId()))
                .goalName("건강 미션 목표")
                .goalDescription("선택된 미션들로 구성된 건강 목표")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(3))
                .status("ACTIVE")
                .build();
        
        GoalSettingEntity savedGoal = goalJpaRepository.save(goalSetting);
        
        // 선택된 미션들을 사용자 미션으로 저장
        request.getSelectedMissionIds().forEach(missionId -> {
            UserMissionEntity userMission = UserMissionEntity.builder()
                    .userId(Long.parseLong(request.getUserId()))
                    .missionId(missionId)
                    .title(getMissionTitle(missionId))
                    .description(getMissionDescription(missionId))
                    .category(getMissionCategory(missionId))
                    .difficulty("중급")
                    .active(true)
                    .streakDays(0)
                    .totalCompletedCount(0)
                    .build();
            
            userMissionJpaRepository.save(userMission);
        });
        
        log.info("목표 설정 저장 완료: goalId={}, userId={}", savedGoal.getId(), request.getUserId());
        return savedGoal.getId().toString();
    }
    
    @Override
    public List<DailyMission> findActiveMissionsByUserId(String userId) {
        List<UserMissionEntity> activeEntities = userMissionJpaRepository
                .findByUserIdAndActiveTrue(Long.parseLong(userId));
        
        return activeEntities.stream()
                .map(entity -> DailyMission.builder()
                        .missionId(entity.getMissionId())
                        .title(entity.getTitle())
                        .description(entity.getDescription())
                        .status("ACTIVE")
                        .completedToday(isCompletedToday(entity.getUserId(), entity.getMissionId()))
                        .streakDays(entity.getStreakDays())
                        .nextReminderTime("09:00")
                        .build())
                .toList();
    }
    
    @Override
    public void deactivateCurrentMissions(String userId) {
        List<UserMissionEntity> activeMissions = userMissionJpaRepository
                .findByUserIdAndActiveTrue(Long.parseLong(userId));
        
        activeMissions.forEach(mission -> {
            mission.deactivate();
            userMissionJpaRepository.save(mission);
        });
        
        log.info("현재 미션 비활성화 완료: userId={}, missionCount={}", userId, activeMissions.size());
    }
    
    @Override
    public void recordMissionCompletion(String missionId, MissionCompleteRequest request) {
        // 미션 완료 기록 저장
        MissionCompletionHistoryEntity completion = MissionCompletionHistoryEntity.builder()
                .userId(Long.parseLong(request.getUserId()))
                .missionId(missionId)
                .completedDate(LocalDate.now())
                .completedAt(LocalDateTime.now())
                .notes(request.getNotes())
                .streakDaysAtCompletion(0) // 실제 구현에서는 계산
                .build();
        
        missionCompletionJpaRepository.save(completion);
        
        // 사용자 미션 통계 업데이트
        UserMissionEntity userMission = userMissionJpaRepository
                .findByUserIdAndMissionId(Long.parseLong(request.getUserId()), missionId)
                .orElseThrow(() -> new IllegalArgumentException("미션을 찾을 수 없습니다."));
        
        userMission.incrementCompletedCount();
        userMissionJpaRepository.save(userMission);
        
        log.info("미션 완료 기록 저장: userId={}, missionId={}", request.getUserId(), missionId);
    }
    
    @Override
    public int getTotalCompletedCount(String userId, String missionId) {
        return missionCompletionJpaRepository
                .countByUserIdAndMissionId(Long.parseLong(userId), missionId);
    }
    
    @Override
    public List<MissionStats> findMissionHistoryByPeriod(String userId, String startDate, String endDate, String missionIds) {
        // 실제 구현에서는 DB에서 통계 조회
        // Mock 데이터 반환
        return List.of(
                MissionStats.builder()
                        .missionId("mission_1")
                        .title("하루 8잔 물마시기")
                        .achievementRate(85.0)
                        .completedDays(17)
                        .totalDays(20)
                        .build(),
                MissionStats.builder()
                        .missionId("mission_2")
                        .title("점심시간 10분 산책")
                        .achievementRate(75.0)
                        .completedDays(15)
                        .totalDays(20)
                        .build(),
                MissionStats.builder()
                        .missionId("mission_3")
                        .title("어깨 스트레칭 3세트")
                        .achievementRate(95.0)
                        .completedDays(19)
                        .totalDays(20)
                        .build()
        );
    }
    
    /**
     * 오늘 완료 여부를 확인합니다.
     * 
     * @param userId 사용자 ID
     * @param missionId 미션 ID
     * @return 오늘 완료 여부
     */
    private boolean isCompletedToday(Long userId, String missionId) {
        return missionCompletionJpaRepository
                .existsByUserIdAndMissionIdAndCompletedDate(userId, missionId, LocalDate.now());
    }
    
    /**
     * 미션 제목을 반환합니다.
     * 
     * @param missionId 미션 ID
     * @return 미션 제목
     */
    private String getMissionTitle(String missionId) {
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
     * 미션 카테고리를 반환합니다.
     * 
     * @param missionId 미션 ID
     * @return 미션 카테고리
     */
    private String getMissionCategory(String missionId) {
        return switch (missionId) {
            case "mission_1", "mission_5" -> "생활습관";
            case "mission_2", "mission_3", "mission_4" -> "운동";
            default -> "기타";
        };
    }
}
