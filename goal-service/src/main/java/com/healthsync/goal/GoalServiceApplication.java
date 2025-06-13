package com.healthsync.goal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Goal Service의 메인 애플리케이션 클래스입니다.
 * 사용자의 건강 목표 설정 및 미션 관리 기능을 제공합니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@SpringBootApplication(scanBasePackages = {"com.healthsync.goal", "com.healthsync.common"})
@EnableJpaAuditing
@ConfigurationPropertiesScan
public class GoalServiceApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(GoalServiceApplication.class, args);
    }
}
