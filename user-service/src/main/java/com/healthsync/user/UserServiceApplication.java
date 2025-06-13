package com.healthsync.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * User Service의 메인 애플리케이션 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@SpringBootApplication(scanBasePackages = {"com.healthsync.user", "com.healthsync.common"})
@EnableJpaAuditing
@ConfigurationPropertiesScan
public class UserServiceApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}
