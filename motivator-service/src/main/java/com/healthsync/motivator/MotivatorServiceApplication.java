package com.healthsync.motivator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Motivator Service의 메인 애플리케이션 클래스입니다.
 * 사용자 동기부여 메시지 생성 및 배치 알림 처리 기능을 제공합니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@SpringBootApplication(scanBasePackages = {"com.healthsync.motivator", "com.healthsync.common"})
@EnableJpaAuditing
@ConfigurationPropertiesScan
@EnableBatchProcessing
@EnableScheduling
public class MotivatorServiceApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(MotivatorServiceApplication.class, args);
    }
}
