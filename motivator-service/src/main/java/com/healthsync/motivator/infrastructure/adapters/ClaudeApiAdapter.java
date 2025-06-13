package com.healthsync.motivator.infrastructure.adapters;

import com.healthsync.common.exception.ExternalApiException;
import com.healthsync.motivator.infrastructure.ports.ClaudeApiPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Claude API와의 통신을 담당하는 어댑터 클래스입니다.
 * Clean Architecture의 Infrastructure 계층에 해당합니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ClaudeApiAdapter implements ClaudeApiPort {
    
    private final WebClient webClient = WebClient.builder().build();
    
    @Value("${claude.api.url}")
    private String claudeApiUrl;
    
    @Value("${claude.api.key}")
    private String claudeApiKey;
    
    @Value("${claude.api.model}")
    private String claudeModel;
    
    @Value("${claude.api.max-tokens}")
    private int maxTokens;
    
    @Override
    public String callClaudeApi(String prompt) {
        try {
            log.info("Claude API 호출: promptLength={}", prompt.length());
            
            // 실제 구현에서는 Claude API 호출
            // Mock 응답 반환
            return generateMockMotivationMessage(prompt);
                    
        } catch (Exception e) {
            log.error("Claude API 호출 실패: error={}", e.getMessage(), e);
            throw new ExternalApiException("AI 메시지 생성에 실패했습니다.");
        }
    }
    
    /**
     * Mock 동기부여 메시지를 생성합니다.
     * 
     * @param prompt 프롬프트
     * @return Mock 동기부여 메시지
     */
    private String generateMockMotivationMessage(String prompt) {
        if (prompt.contains("완료율") && prompt.contains("낮")) {
            return "🌟 포기하지 마세요! 작은 변화가 큰 결과를 만듭니다. 오늘 한 가지만 더 해볼까요?";
        } else if (prompt.contains("연속")) {
            return "🔥 연속 달성 중이시군요! 이 멋진 흐름을 계속 이어가봐요!";
        } else if (prompt.contains("배치")) {
            return "💪 새로운 하루가 시작됐어요! 건강한 습관으로 오늘도 화이팅!";
        } else if (prompt.contains("실패")) {
            return "🌅 괜찮아요! 다시 시작하는 것이 중요해요. 오늘부터 새롭게 도전해봐요!";
        } else {
            return "✨ 건강한 하루 만들기, 함께 해요! 작은 실천이 큰 변화를 만듭니다!";
        }
    }
}
