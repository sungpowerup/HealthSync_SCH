package com.healthsync.motivator.domain.services;

import com.healthsync.motivator.dto.*;
import com.healthsync.motivator.infrastructure.ports.ClaudeApiPort;
import com.healthsync.motivator.infrastructure.ports.CachePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 배치 처리를 담당하는 도메인 서비스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BatchProcessingDomainService {
    
    private final MessageGenerationDomainService messageGenerationDomainService;
    private final UserAnalysisDomainService userAnalysisDomainService;
    
    /**
     * 알림이 필요한 사용자를 필터링합니다.
     * 
     * @param allUsers 전체 사용자 목록
     * @param request 배치 요청
     * @return 필터링된 사용자 목록
     */
    public List<UserMissionStatus> filterUsersNeedingNotification(
            List<UserMissionStatus> allUsers, BatchNotificationRequest request) {
        
        LocalTime currentTime = LocalTime.now();
        
        return allUsers.stream()
                .filter(user -> isEligibleForNotification(user, currentTime))
                .filter(user -> request.getTargetUsers().isEmpty() || 
                               request.getTargetUsers().contains(user.getUserId()))
                .collect(Collectors.toList());
    }
    
    /**
     * 사용자를 긴급도별로 우선순위를 정렬합니다.
     * 
     * @param users 사용자 목록
     * @return 우선순위별 정렬된 사용자 목록
     */
    public List<UserMissionStatus> prioritizeUsersByUrgency(List<UserMissionStatus> users) {
        return users.stream()
                .sorted(Comparator
                        .comparing((UserMissionStatus u) -> calculateUrgencyScore(u))
                        .reversed()
                        .thenComparing(UserMissionStatus::getLastActiveTime))
                .collect(Collectors.toList());
    }
    
    /**
     * 배치 알림을 처리합니다.
     * 
     * @param prioritizedUsers 우선순위별 사용자 목록
     * @param batchId 배치 ID
     * @param claudeApiPort Claude API 포트
     * @param cachePort 캐시 포트
     * @return 배치 처리 결과
     */
    public BatchProcessingResult processBatchNotifications(
            List<UserMissionStatus> prioritizedUsers, 
            String batchId,
            ClaudeApiPort claudeApiPort,
            CachePort cachePort) {
        
        log.info("배치 알림 처리 시작: batchId={}, userCount={}", batchId, prioritizedUsers.size());
        
        int processedCount = 0;
        int successCount = 0;
        int failedCount = 0;
        
        for (UserMissionStatus userStatus : prioritizedUsers) {
            try {
                processedCount++;
                
                // 사용자별 알림 컨텍스트 분석
                UserNotificationContext context = analyzeUserNotificationContext(userStatus);
                
                // 개인화된 배치 메시지 생성
                String message = generateBatchEncouragementMessage(context, claudeApiPort);
                
                // 배치 메시지 캐시에 저장
                cachePort.storeBatchMessage(userStatus.getUserId(), message);
                
                successCount++;
                
                log.debug("배치 알림 처리 성공: userId={}, batchId={}", userStatus.getUserId(), batchId);
                
            } catch (Exception e) {
                failedCount++;
                log.error("배치 알림 처리 실패: userId={}, batchId={}, error={}", 
                        userStatus.getUserId(), batchId, e.getMessage(), e);
            }
        }
        
        log.info("배치 알림 처리 완료: batchId={}, processed={}, success={}, failed={}", 
                batchId, processedCount, successCount, failedCount);
        
        return BatchProcessingResult.builder()
                .batchId(batchId)
                .processedCount(processedCount)
                .successCount(successCount)
                .failedCount(failedCount)
                .build();
    }
    
    /**
     * 알림 대상 여부를 확인합니다.
     * 
     * @param user 사용자 미션 상태
     * @param currentTime 현재 시간
     * @return 알림 대상 여부
     */
    private boolean isEligibleForNotification(UserMissionStatus user, LocalTime currentTime) {
        // 미션 완료율이 낮거나 연속 실패 시 알림 대상
        double completionRate = calculateCompletionRate(user);
        int consecutiveFailures = calculateConsecutiveFailures(user);
        
        // 조용한 시간대 확인 (밤 10시 ~ 오전 8시)
        boolean isQuietHours = currentTime.isBefore(LocalTime.of(8, 0)) || 
                              currentTime.isAfter(LocalTime.of(22, 0));
        
        return (completionRate < 0.6 || consecutiveFailures >= 2) && !isQuietHours;
    }
    
    /**
     * 긴급도 점수를 계산합니다.
     * 
     * @param user 사용자 미션 상태
     * @return 긴급도 점수 (높을수록 우선순위 높음)
     */
    private int calculateUrgencyScore(UserMissionStatus user) {
        int score = 0;
        
        // 완료율이 낮을수록 높은 점수
        double completionRate = calculateCompletionRate(user);
        score += (int) ((1.0 - completionRate) * 50);
        
        // 연속 실패 일수에 따른 점수
        int consecutiveFailures = calculateConsecutiveFailures(user);
        score += consecutiveFailures * 20;
        
        // 마지막 활동 시간이 오래될수록 높은 점수
        long daysSinceLastActive = java.time.Duration.between(
                user.getLastActiveTime(), java.time.LocalDateTime.now()).toDays();
        score += (int) Math.min(daysSinceLastActive * 5, 30);
        
        return score;
    }
    
    /**
     * 완료율을 계산합니다.
     * 
     * @param user 사용자 미션 상태
     * @return 완료율
     */
    private double calculateCompletionRate(UserMissionStatus user) {
        if (user.getTotalMissions() == 0) return 0.0;
        return (double) user.getCompletedMissions() / user.getTotalMissions();
    }
    
    /**
     * 연속 실패 일수를 계산합니다.
     * 
     * @param user 사용자 미션 상태
     * @return 연속 실패 일수
     */
    private int calculateConsecutiveFailures(UserMissionStatus user) {
        // 실제 구현에서는 사용자의 최근 미션 이력을 분석
        // Mock 데이터로 계산
        double completionRate = calculateCompletionRate(user);
        if (completionRate < 0.3) return 3;
        else if (completionRate < 0.5) return 2;
        else if (completionRate < 0.7) return 1;
        else return 0;
    }
    
    /**
     * 사용자 알림 컨텍스트를 분석합니다.
     * 
     * @param userStatus 사용자 미션 상태
     * @return 사용자 알림 컨텍스트
     */
    private UserNotificationContext analyzeUserNotificationContext(UserMissionStatus userStatus) {
        return UserNotificationContext.builder()
                .userId(userStatus.getUserId())
                .completionRate(calculateCompletionRate(userStatus))
                .consecutiveFailures(calculateConsecutiveFailures(userStatus))
                .lastActiveTime(userStatus.getLastActiveTime())
                .totalMissions(userStatus.getTotalMissions())
                .completedMissions(userStatus.getCompletedMissions())
                .build();
    }
    
    /**
     * 배치 독려 메시지를 생성합니다.
     * 
     * @param context 사용자 알림 컨텍스트
     * @param claudeApiPort Claude API 포트
     * @return 배치 독려 메시지
     */
    private String generateBatchEncouragementMessage(UserNotificationContext context, ClaudeApiPort claudeApiPort) {
        try {
            String prompt = prepareBatchPrompt(context);
            String aiResponse = claudeApiPort.callClaudeApi(prompt);
            return addPersonalizedTouch(aiResponse, context);
        } catch (Exception e) {
            log.warn("배치 AI 메시지 생성 실패, 기본 메시지 사용: userId={}", context.getUserId());
            return generateFallbackBatchMessage(context);
        }
    }
    
    /**
     * 배치 프롬프트를 준비합니다.
     * 
     * @param context 사용자 알림 컨텍스트
     * @return 배치 프롬프트
     */
    private String prepareBatchPrompt(UserNotificationContext context) {
        return String.format("""
            건강 코치로서 사용자에게 따뜻한 배치 알림 메시지를 작성해주세요.
            
            [상황]
            - 완료율: %.1f%%
            - 연속 실패: %d일
            - 마지막 활동: %s
            
            [요구사항]
            - 80자 이내 간결한 메시지
            - 부담스럽지 않고 격려하는 톤
            - 오늘 다시 시작할 수 있다는 희망적 메시지
            - 이모지 활용하여 친근함 표현
            """,
            context.getCompletionRate() * 100,
            context.getConsecutiveFailures(),
            context.getLastActiveTime().toLocalDate()
        );
    }
    
    /**
     * 개인화된 터치를 추가합니다.
     * 
     * @param message 기본 메시지
     * @param context 사용자 컨텍스트
     * @return 개인화된 메시지
     */
    private String addPersonalizedTouch(String message, UserNotificationContext context) {
        // 메시지 길이 제한 및 개인화 요소 추가
        String personalizedMessage = message.trim();
        
        if (personalizedMessage.length() > 80) {
            personalizedMessage = personalizedMessage.substring(0, 77) + "...";
        }
        
        return personalizedMessage;
    }
    
    /**
     * 기본 배치 메시지를 생성합니다.
     * 
     * @param context 사용자 컨텍스트
     * @return 기본 배치 메시지
     */
    private String generateFallbackBatchMessage(UserNotificationContext context) {
        if (context.getConsecutiveFailures() >= 3) {
            return "🌅 새로운 하루, 새로운 시작! 작은 한 걸음부터 다시 시작해봐요!";
        } else if (context.getCompletionRate() < 0.5) {
            return "💪 포기하지 마세요! 오늘부터 다시 건강한 습관을 만들어가요!";
        } else {
            return "🎯 조금만 더 힘내세요! 건강한 목표까지 거의 다 왔어요!";
        }
    }
}
