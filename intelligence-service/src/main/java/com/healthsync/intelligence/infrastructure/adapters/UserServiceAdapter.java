package com.healthsync.intelligence.infrastructure.adapters;

import com.healthsync.common.exception.ExternalApiException;
import com.healthsync.intelligence.dto.UserProfile;
import com.healthsync.intelligence.infrastructure.ports.UserServicePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * User Service와의 통신을 담당하는 어댑터 클래스입니다.
 * Clean Architecture의 Infrastructure 계층에 해당합니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserServiceAdapter implements UserServicePort {
    
    private final WebClient webClient = WebClient.builder().build();
    
    @Value("${services.user-service.url}")
    private String userServiceUrl;
    
    @Override
    public UserProfile getUserProfile(String userId) {
        try {
            log.info("User Service 사용자 프로필 조회: userId={}", userId);
            
            // 실제 구현에서는 User Service API 호출
            // Mock 데이터 반환
            return UserProfile.builder()
                    .userId(userId)
                    .name("홍길동")
                    .age(32)
                    .gender("남성")
                    .occupation("IT개발")
                    .build();
                    
        } catch (Exception e) {
            log.error("User Service 호출 실패: userId={}, error={}", userId, e.getMessage(), e);
            throw new ExternalApiException("사용자 프로필 조회에 실패했습니다.");
        }
    }
}
