package com.healthsync.motivator.application_services;

import com.healthsync.motivator.domain.services.UserAnalysisDomainService;
import com.healthsync.motivator.domain.services.MessageGenerationDomainService;
import com.healthsync.motivator.domain.services.BatchProcessingDomainService;
import com.healthsync.motivator.domain.repositories.NotificationRepository;
import com.healthsync.motivator.dto.*;
import com.healthsync.motivator.infrastructure.ports.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 동기부여 관련 유스케이스입니다.
 * Clean Architecture의 Application Service 계층에 해당합니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MotivationUseCase {
    
    private final UserAnalysisDomainService userAnalysisDomainService;
    private final MessageGenerationDomainService messageGenerationDomainService;
    private final BatchProcessingDomainService batchProcessingDomainService;
    private final NotificationRepository notificationRepository;
    private final GoalServicePort goalServicePort;
    private final ClaudeApiPort claudeApiPort;
    private final CachePort cachePort;
    private final EventPublisherPort eventPublisherPort;
    
    /**
     * 독려 메시지를 생성합니다.
     * 
     * @param request 독려 요청
     * @return 독려 응답
     */
    public EncouragementResponse generateEncouragementMessage(EncouragementRequest request) {
        log.info("독려 메시지 생성 시작: userId={}", request.getUserId());
        
        // 요청 검증
        validateEncouragementRequest(request);
        
        // 캐시에서 기존 메시지 확인
        String cacheKey = generateCacheKey(request.getUserId(), request.getMissionsStatus());
        EncouragementResponse cachedResponse = cachePort.getCachedEncouragementMessage(cacheKey);
        if (cachedResponse != null) {
            log.info("캐시에서 독려 메시지 조회: userId={}", request.getUserId());
            return cachedResponse;
        }
        
        // 사용자 미션 진행 상황 분석
        DailyProgress dailyProgress = goalServicePort.getUserDailyProgress(request.getUserId());
        ProgressAnalysis progressAnalysis = userAnalysisDomainService.analyzeMissionProgress(
                request.getUserId(), request.getMissionsStatus(), dailyProgress);
        
        // AI 독려 메시지 생성
        String encouragementMessage = messageGenerationDomainService.generateEncouragementMessage(
                progressAnalysis, claudeApiPort);
        
        // 응답 구성
        EncouragementResponse response = EncouragementResponse.builder()
                .message(encouragementMessage)
                .motivationType(progressAnalysis.getMotivationType().name())
                .timing(calculateOptimalTiming(progressAnalysis))
                .personalizedTip(generatePersonalizedTip(progressAnalysis))
                .priority(progressAnalysis.getUrgencyLevel().name())
                .build();
        
        // 캐시에 저장
        cachePort.cacheEncouragementMessage(cacheKey, response);
        
        // 알림 로그 저장
        notificationRepository.saveNotificationLog(request.getUserId(), null, "encouragement", encouragementMessage);
        
        // 이벤트 발행
        eventPublisherPort.publishEncouragementSentEvent(request.getUserId(), "encouragement");
        
        log.info("독려 메시지 생성 완료: userId={}, motivationType={}", 
                request.getUserId(), response.getMotivationType());
        return response;
    }
    
    /**
     * 배치 알림을 처리합니다.
     * 
     * @param request 배치 알림 요청
     * @return 배치 처리 결과
     */
    public BatchNotificationResponse processBatchNotifications(BatchNotificationRequest request) {
        log.info("배치 알림 처리 시작: triggerTime={}, targetCount={}", 
                request.getTriggerTime(), request.getTargetUsers().size());
        
        // 배치 처리 시작
        String batchId = generateBatchId();
        LocalDateTime startTime = LocalDateTime.now();
        
        // 전체 사용자 미션 상태 조회
        List<UserMissionStatus> allUsersStatus = goalServicePort.getAllUsersWithActiveMissions();
        
        // 대상 사용자 필터링
        List<UserMissionStatus> targetUsers = batchProcessingDomainService
                .filterUsersNeedingNotification(allUsersStatus, request);
        
        // 우선순위별 정렬
        List<UserMissionStatus> prioritizedUsers = batchProcessingDomainService
                .prioritizeUsersByUrgency(targetUsers);
        
        // 배치 처리 실행
        BatchProcessingResult result = batchProcessingDomainService
                .processBatchNotifications(prioritizedUsers, batchId, claudeApiPort, cachePort);
        
        // 처리 결과 로깅
        LocalDateTime endTime = LocalDateTime.now();
        log.info("배치 알림 처리 완료: batchId={}, processed={}, success={}, failed={}, duration={}ms", 
                batchId, result.getProcessedCount(), result.getSuccessCount(), 
                result.getFailedCount(), java.time.Duration.between(startTime, endTime).toMillis());
        
        // 다음 스케줄 시간 계산
        String nextScheduledTime = calculateNextScheduledTime(request.getTriggerTime());
        
        return BatchNotificationResponse.builder()
                .batchId(batchId)
                .processedCount(result.getProcessedCount())
                .successCount(result.getSuccessCount())
                .failedCount(result.getFailedCount())
                .nextScheduledTime(nextScheduledTime)
                .build();
    }
    
    /**
     * 독려 요청을 검증합니다.
     * 
     * @param request 독려 요청
     */
    private void validateEncouragementRequest(EncouragementRequest request) {
        if (request.getUserId() == null || request.getUserId().trim().isEmpty()) {
            throw new IllegalArgumentException("사용자 ID는 필수입니다.");
        }
        
        if (request.getMissionsStatus() == null || request.getMissionsStatus().isEmpty()) {
            throw new IllegalArgumentException("미션 상태 정보는 필수입니다.");
        }
    }
    
    /**
     * 캐시 키를 생성합니다.
     * 
     * @param userId 사용자 ID
     * @param missionsStatus 미션 상태 목록
     * @return 캐시 키
     */
    private String generateCacheKey(String userId, List<MissionStatus> missionsStatus) {
        // 미션 완료 상태를 바탕으로 캐시 키 생성
        long completedCount = missionsStatus.stream().mapToLong(ms -> ms.isCompleted() ? 1 : 0).sum();
        double completionRate = (double) completedCount / missionsStatus.size();
        
        return String.format("encouragement:%s:%.1f", userId, completionRate);
    }
    
    /**
     * 최적 타이밍을 계산합니다.
     * 
     * @param progressAnalysis 진행 분석
     * @return 최적 타이밍
     */
    private String calculateOptimalTiming(ProgressAnalysis progressAnalysis) {
        return switch (progressAnalysis.getUrgencyLevel()) {
            case HIGH -> "즉시";
            case MEDIUM -> "1시간 후";
            case LOW -> "내일 오전";
        };
    }
    
    /**
     * 개인화된 팁을 생성합니다.
     * 
     * @param progressAnalysis 진행 분석
     * @return 개인화된 팁
     */
    private String generatePersonalizedTip(ProgressAnalysis progressAnalysis) {
        return switch (progressAnalysis.getMotivationType()) {
            case ACHIEVEMENT -> "작은 목표부터 차근차근 달성해보세요!";
            case SOCIAL -> "친구들과 함께 도전하면 더 재미있어요!";
            case HEALTH_BENEFIT -> "건강한 습관이 당신의 미래를 바꿉니다!";
            case HABIT_FORMATION -> "21일만 지속하면 습관이 됩니다!";
        };
    }
    
    /**
     * 배치 ID를 생성합니다.
     * 
     * @return 배치 ID
     */
    private String generateBatchId() {
        return "batch_" + System.currentTimeMillis();
    }
    
    /**
     * 다음 스케줄 시간을 계산합니다.
     * 
     * @param triggerTime 현재 트리거 시간
     * @return 다음 스케줄 시간
     */
    private String calculateNextScheduledTime(String triggerTime) {
        // 실제 구현에서는 cron 표현식 등을 활용하여 계산
        return LocalDateTime.now().plusHours(2).toString();
    }
}
