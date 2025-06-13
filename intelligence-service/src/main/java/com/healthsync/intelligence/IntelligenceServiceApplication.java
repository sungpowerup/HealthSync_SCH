package com.healthsync.intelligence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Intelligence Service의 메인 애플리케이션 클래스입니다.
 * AI 기반 건강 분석, 미션 추천, 채팅 상담 기능을 제공합니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@SpringBootApplication(scanBasePackages = {"com.healthsync.intelligence", "com.healthsync.common"})
@EnableJpaAuditing
@ConfigurationPropertiesScan
public class IntelligenceServiceApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(IntelligenceServiceApplication.class, args);
    }
}
