package com.healthsync.gateway.config;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import reactor.core.publisher.Mono;

/**
 * Gateway 설정을 관리하는 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Configuration
public class GatewayConfig {
    
    /**
     * 요청/응답 로깅을 위한 글로벌 필터를 설정합니다.
     * 
     * @return 로깅 글로벌 필터
     */
    @Bean
    @Order(-1)
    public GlobalFilter loggingFilter() {
        return (exchange, chain) -> {
            String requestPath = exchange.getRequest().getPath().value();
            String requestMethod = exchange.getRequest().getMethod().name();
            
            long startTime = System.currentTimeMillis();
            
            return chain.filter(exchange).then(
                Mono.fromRunnable(() -> {
                    long endTime = System.currentTimeMillis();
                    long duration = endTime - startTime;
                    
                    System.out.printf("Gateway: %s %s - %dms%n", 
                            requestMethod, requestPath, duration);
                })
            );
        };
    }
}
