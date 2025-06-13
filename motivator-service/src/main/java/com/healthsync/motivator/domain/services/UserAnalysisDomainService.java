package com.healthsync.motivator.domain.services;

import com.healthsync.motivator.dto.*;
import com.healthsync.motivator.enums.MotivationType;
import com.healthsync.motivator.enums.UrgencyLevel;
import com.healthsync.motivator.enums.EngagementLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 사용자 분석을 담당하는 도메인 서비스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserAnalysisDomainService {
    
    /**
     * 미션 진행 상황을 분석합니다.
     * 
     * @param userId 사용자 ID
     * @param missionsStatus 미션 상태 목록
     * @param dailyProgress 일일 진행 상황
     * @return 진행 분석 결과
     */
    public ProgressAnalysis analyzeMissionProgress(String userId, List<MissionStatus> missionsStatus, DailyProgress dailyProgress) {
        log.info("미션 진행 상황 분석: userId={}, missionCount={}", userId, missionsStatus.size());
        
        // 진행률 계산
        long completedCount = missionsStatus.stream().mapToLong(ms -> ms.isCompleted() ? 1 : 0).sum();
        double completionRate = (double) completedCount / missionsStatus.size();
        
        // 실패 포인트 식별
        List<String> failurePoints = identifyFailurePoints(missionsStatus);
        
        // 진행 패턴 계산
        String progressPattern = calculateProgressPatterns(dailyProgress);
        
        // 동기부여 유형 결정
        MotivationType motivationType = determineMotivationType(completionRate, progressPattern);
        
        // 긴급도 수준 결정
        UrgencyLevel urgencyLevel = determineUrgencyLevel(completionRate, failurePoints.size());
        
        // 참여도 수준 결정
        EngagementLevel engagementLevel = determineEngagementLevel(dailyProgress);
        
        return ProgressAnalysis.builder()
                .userId(userId)
                .completionRate(completionRate)
                .completedMissionsCount((int) completedCount)
                .totalMissionsCount(missionsStatus.size())
                .failurePoints(failurePoints)
                .progressPattern(progressPattern)
                .motivationType(motivationType)
                .urgencyLevel(urgencyLevel)
                .engagementLevel(engagementLevel)
                .streakDays(dailyProgress.getCurrentStreak())
                .build();
    }
    
    /**
     * 실패 포인트를 식별합니다.
     * 
     * @param missionsStatus 미션 상태 목록
     * @return 실패 포인트 목록
     */
    private List<String> identifyFailurePoints(List<MissionStatus> missionsStatus) {
        return missionsStatus.stream()
                .filter(ms -> !ms.isCompleted())
                .map(MissionStatus::getMissionId)
                .toList();
    }
    
    /**
     * 진행 패턴을 계산합니다.
     * 
     * @param dailyProgress 일일 진행 상황
     * @return 진행 패턴
     */
    private String calculateProgressPatterns(DailyProgress dailyProgress) {
        if (dailyProgress.getCurrentStreak() >= 7) {
            return "consistent_high_performer";
        } else if (dailyProgress.getCurrentStreak() >= 3) {
            return "steady_improver";
        } else if (dailyProgress.getWeeklyCompletionRate() >= 0.7) {
            return "weekend_warrior";
        } else {
            return "needs_support";
        }
    }
    
    /**
     * 동기부여 유형을 결정합니다.
     * 
     * @param completionRate 완료율
     * @param progressPattern 진행 패턴
     * @return 동기부여 유형
     */
    private MotivationType determineMotivationType(double completionRate, String progressPattern) {
        if (completionRate >= 0.8) {
            return MotivationType.ACHIEVEMENT;
        } else if (completionRate >= 0.5) {
            return MotivationType.HABIT_FORMATION;
        } else if ("weekend_warrior".equals(progressPattern)) {
            return MotivationType.SOCIAL;
        } else {
            return MotivationType.HEALTH_BENEFIT;
        }
    }
    
    /**
     * 긴급도 수준을 결정합니다.
     * 
     * @param completionRate 완료율
     * @param failureCount 실패 개수
     * @return 긴급도 수준
     */
    private UrgencyLevel determineUrgencyLevel(double completionRate, int failureCount) {
        if (completionRate < 0.3 || failureCount >= 3) {
            return UrgencyLevel.HIGH;
        } else if (completionRate < 0.6 || failureCount >= 2) {
            return UrgencyLevel.MEDIUM;
        } else {
            return UrgencyLevel.LOW;
        }
    }
    
    /**
     * 참여도 수준을 결정합니다.
     * 
     * @param dailyProgress 일일 진행 상황
     * @return 참여도 수준
     */
    private EngagementLevel determineEngagementLevel(DailyProgress dailyProgress) {
        if (dailyProgress.getWeeklyCompletionRate() >= 0.8) {
            return EngagementLevel.HIGH;
        } else if (dailyProgress.getWeeklyCompletionRate() >= 0.5) {
            return EngagementLevel.MEDIUM;
        } else {
            return EngagementLevel.LOW;
        }
    }
}
