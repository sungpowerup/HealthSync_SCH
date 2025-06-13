package com.healthsync.motivator.infrastructure.adapters;

import com.healthsync.common.exception.ExternalApiException;
import com.healthsync.motivator.dto.DailyProgress;
import com.healthsync.motivator.dto.UserMissionStatus;
import com.healthsync.motivator.infrastructure.ports.GoalServicePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Goal Service와의 통신을 담당하는 어댑터 클래스입니다.
 * Clean Architecture의 Infrastructure 계층에 해당합니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class GoalServiceAdapter implements GoalServicePort {
    
    private final WebClient webClient = WebClient.builder().build();
    
    @Value("${services.goal-service.url}")
    private String goalServiceUrl;
    
    @Override
    public DailyProgress getUserDailyProgress(String userId) {
        try {
            log.info("Goal Service 사용자 일일 진행 상황 조회: userId={}", userId);
            
            // 실제 구현에서는 Goal Service API 호출
            // Mock 데이터 반환
            return DailyProgress.builder()
                    .currentStreak(5)
                    .weeklyCompletionRate(0.75)
                    .todayCompletedCount(3)
                    .todayTotalCount(5)
                    .build();
                    
        } catch (Exception e) {
            log.error("Goal Service 일일 진행 상황 조회 실패: userId={}, error={}", userId, e.getMessage(), e);
            throw new ExternalApiException("사용자 일일 진행 상황 조회에 실패했습니다.");
        }
    }
    
    @Override
    public List<UserMissionStatus> getAllUsersWithActiveMissions() {
        try {
            log.info("Goal Service 활성 미션 사용자 조회");
            
            // 실제 구현에서는 Goal Service API 호출
            // Mock 데이터 반환
            return IntStream.range(1, 11)
                    .mapToObj(i -> UserMissionStatus.builder()
                            .userId("user_" + i)
                            .totalMissions(5)
                            .completedMissions(i % 3 + 1) // 1-3개 완료
                            .lastActiveTime(LocalDateTime.now().minusHours(i))
                            .build())
                    .toList();
                    
        } catch (Exception e) {
            log.error("Goal Service 활성 미션 사용자 조회 실패: error={}", e.getMessage(), e);
            throw new ExternalApiException("활성 미션 사용자 조회에 실패했습니다.");
        }
    }
}
