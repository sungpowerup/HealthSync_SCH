package com.healthsync.goal.domain.services;

import com.healthsync.common.exception.ValidationException;
import com.healthsync.goal.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 목표 관련 비즈니스 로직을 처리하는 도메인 서비스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GoalDomainService {
    
    /**
     * 미션 선택을 검증합니다.
     * 
     * @param request 미션 선택 요청
     */
    public void validateMissionSelection(MissionSelectionRequest request) {
        if (request.getSelectedMissionIds() == null || request.getSelectedMissionIds().isEmpty()) {
            throw new ValidationException("최소 1개 이상의 미션을 선택해야 합니다.");
        }
        
        if (request.getSelectedMissionIds().size() > 5) {
            throw new ValidationException("최대 5개까지 미션을 선택할 수 있습니다.");
        }
        
        // 중복 미션 검사
        if (request.getSelectedMissionIds().size() != request.getSelectedMissionIds().stream().distinct().count()) {
            throw new ValidationException("중복된 미션이 선택되었습니다.");
        }
        
        log.info("미션 선택 검증 완료: userId={}, missionCount={}", request.getUserId(), request.getSelectedMissionIds().size());
    }
    
    /**
     * 미션 완료를 검증합니다.
     * 
     * @param missionId 미션 ID
     * @param userId 사용자 ID
     */
    public void validateMissionCompletion(String missionId, String userId) {
        if (missionId == null || missionId.trim().isEmpty()) {
            throw new ValidationException("미션 ID가 필요합니다.");
        }
        
        if (userId == null || userId.trim().isEmpty()) {
            throw new ValidationException("사용자 ID가 필요합니다.");
        }
        
        log.info("미션 완료 검증 완료: userId={}, missionId={}", userId, missionId);
    }
    
    /**
     * 연속 달성 일수를 계산합니다.
     * 
     * @param userId 사용자 ID
     * @param missionId 미션 ID
     * @return 연속 달성 일수
     */
    public int calculateStreakDays(String userId, String missionId) {
        // 실제 구현에서는 DB에서 연속 달성 일수 계산
        // Mock 데이터로 반환
        return (int) (Math.random() * 10) + 1;
    }
    
    /**
     * 달성 통계를 계산합니다.
     * 
     * @param userId 사용자 ID
     * @param startDate 시작일
     * @param endDate 종료일
     * @return 달성 통계
     */
    public AchievementStats calculateAchievementStats(String userId, String startDate, String endDate) {
        log.info("달성 통계 계산: userId={}, period={} to {}", userId, startDate, endDate);
        
        // 실제 구현에서는 DB 쿼리로 통계 계산
        // Mock 데이터로 반환
        return AchievementStats.builder()
                .totalAchievementRate(75.5)
                .periodAchievementRate(82.3)
                .bestStreak(12)
                .completedDays(15)
                .totalDays(20)
                .build();
    }
    
    /**
     * 차트 데이터를 생성합니다.
     * 
     * @param missionStats 미션 통계 목록
     * @return 차트 데이터
     */
    public Object generateChartData(List<MissionStats> missionStats) {
        Map<String, Object> chartData = new HashMap<>();
        
        // 미션별 달성률 데이터
        List<Map<String, Object>> achievementData = missionStats.stream()
                .map(stat -> {
                    Map<String, Object> data = new HashMap<>();
                    data.put("missionTitle", stat.getTitle());
                    data.put("achievementRate", stat.getAchievementRate());
                    data.put("completedDays", stat.getCompletedDays());
                    return data;
                })
                .toList();
        
        chartData.put("achievementByMission", achievementData);
        chartData.put("chartType", "bar");
        chartData.put("title", "미션별 달성률");
        
        return chartData;
    }
    
    /**
     * 진행 패턴을 분석하여 인사이트를 생성합니다.
     * 
     * @param missionStats 미션 통계 목록
     * @return 인사이트 목록
     */
    public List<String> analyzeProgressPatterns(List<MissionStats> missionStats) {
        // 실제 구현에서는 통계 데이터를 분석하여 패턴 발견
        return List.of(
                "💪 운동 미션의 달성률이 생활습관 미션보다 15% 높습니다.",
                "📈 최근 1주일간 미션 달성률이 20% 향상되었습니다.",
                "🎯 '어깨 스트레칭' 미션이 가장 높은 달성률(95%)을 보입니다.",
                "⏰ 오전에 시작하는 미션들의 달성률이 더 높은 경향을 보입니다."
        );
    }
}
