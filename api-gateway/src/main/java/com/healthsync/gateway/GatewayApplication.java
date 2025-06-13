package com.healthsync.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * API Gateway의 메인 애플리케이션 클래스입니다.
 * 모든 서비스로의 요청을 라우팅하는 역할을 담당합니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@SpringBootApplication
public class GatewayApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
