package com.healthsync.motivator.infrastructure.batch;

import com.healthsync.motivator.application_services.MotivationUseCase;
import com.healthsync.motivator.dto.BatchNotificationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDateTime;
import java.util.Collections;

/**
 * 알림 배치 작업을 정의하는 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Slf4j
@Configuration
@Component
@RequiredArgsConstructor
public class NotificationBatchJob {
    
    private final MotivationUseCase motivationUseCase;
    
    /**
     * 알림 배치 작업을 정의합니다.
     * 
     * @param jobRepository Job 저장소
     * @param transactionManager 트랜잭션 매니저
     * @return 알림 배치 Job
     */
    @Bean
    public Job notificationJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("notificationJob", jobRepository)
                .start(notificationStep(jobRepository, transactionManager))
                .build();
    }
    
    /**
     * 알림 배치 스텝을 정의합니다.
     * 
     * @param jobRepository Job 저장소
     * @param transactionManager 트랜잭션 매니저
     * @return 알림 배치 Step
     */
    @Bean
    public Step notificationStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("notificationStep", jobRepository)
                .tasklet(notificationTasklet(), transactionManager)
                .build();
    }
    
    /**
     * 알림 배치 태스클릿을 정의합니다.
     * 
     * @return 알림 배치 Tasklet
     */
    @Bean
    public Tasklet notificationTasklet() {
        return (contribution, chunkContext) -> {
            log.info("배치 알림 작업 시작");
            
            BatchNotificationRequest request = BatchNotificationRequest.builder()
                    .triggerTime(LocalDateTime.now().toString())
                    .targetUsers(Collections.emptyList()) // 전체 사용자 대상
                    .notificationType("daily_encouragement")
                    .build();
            
            motivationUseCase.processBatchNotifications(request);
            
            log.info("배치 알림 작업 완료");
            return RepeatStatus.FINISHED;
        };
    }
    
    /**
     * 스케줄러를 통한 배치 작업 실행 (매일 오전 9시)
     */
    @Scheduled(cron = "0 0 9 * * ?")
    public void executeScheduledNotification() {
        log.info("스케줄된 배치 알림 실행");
        
        BatchNotificationRequest request = BatchNotificationRequest.builder()
                .triggerTime(LocalDateTime.now().toString())
                .targetUsers(Collections.emptyList())
                .notificationType("scheduled_daily")
                .build();
        
        motivationUseCase.processBatchNotifications(request);
    }
}
