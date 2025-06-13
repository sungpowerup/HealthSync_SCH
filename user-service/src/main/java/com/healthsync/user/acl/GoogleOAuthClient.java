package com.healthsync.user.acl;

import com.healthsync.common.exception.ExternalApiException;
import com.healthsync.user.dto.GoogleUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Google OAuth API와의 통신을 담당하는 클라이언트 클래스입니다.
 * Anti-Corruption Layer 패턴을 적용하여 외부 API 의존성을 격리합니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class GoogleOAuthClient {
    
    private final WebClient webClient = WebClient.builder().build();
    
    @Value("${google.oauth.client-id}")
    private String clientId;
    
    @Value("${google.oauth.client-secret}")
    private String clientSecret;
    
    /**
     * Google OAuth 인증 코드를 통해 사용자 정보를 획득합니다.
     * 
     * @param authCode Google OAuth 인증 코드
     * @return Google 사용자 정보
     */
    public GoogleUserInfo getGoogleUserInfo(String authCode) {
        try {
            // 실제 구현에서는 Google OAuth API 호출
            // 여기서는 Mock 데이터 반환
            log.info("Google OAuth 사용자 정보 요청: authCode={}", authCode);
            
            return new GoogleUserInfo(
                    "google_" + System.currentTimeMillis(),
                    "user@example.com",
                    "테스트 사용자",
                    "https://example.com/picture.jpg"
            );
            
        } catch (Exception e) {
            log.error("Google OAuth API 호출 실패: {}", e.getMessage(), e);
            throw new ExternalApiException("Google 인증 서비스 호출에 실패했습니다.");
        }
    }
}
