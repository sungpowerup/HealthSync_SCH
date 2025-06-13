package com.healthsync.health.interface_adapters.adapters;

import com.healthsync.common.exception.ExternalApiException;
import com.healthsync.health.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * User Service와의 통신을 담당하는 어댑터 클래스입니다.
 * Clean Architecture의 Interface Adapter 계층에 해당합니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserServiceAdapter {
    
    private final WebClient webClient = WebClient.builder().build();
    
    @Value("${services.user-service.url}")
    private String userServiceUrl;
    
    /**
     * User Service로부터 사용자 기본 정보를 조회합니다.
     * 
     * @param userId 사용자 ID
     * @return 사용자 기본 정보
     */
    public UserInfo getUserBasicInfo(String userId) {
        try {
            log.info("User Service 사용자 정보 조회: userId={}", userId);
            
            // Mock 데이터 반환 (실제 구현에서는 User Service API 호출)
            return UserInfo.builder()
                    .name("홍길동")
                    .age(32)
                    .gender("남성")
                    .occupation("IT개발")
                    .build();
                    
        } catch (Exception e) {
            log.error("User Service 호출 실패: userId={}, error={}", userId, e.getMessage(), e);
            throw new ExternalApiException("사용자 정보 조회에 실패했습니다.");
        }
    }
}
