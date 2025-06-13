package com.healthsync.intelligence.infrastructure.adapters;

import com.healthsync.common.exception.ExternalApiException;
import com.healthsync.intelligence.infrastructure.ports.ClaudeApiPort;
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
    public String requestAnalysis(String prompt) {
        try {
            log.info("Claude API 분석 요청: promptLength={}", prompt.length());
            
            // 실제 구현에서는 Claude API 호출
            // Mock 응답 반환
            if (prompt.contains("건강검진")) {
                return generateMockHealthAnalysis();
            } else if (prompt.contains("미션")) {
                return generateMockMissionRecommendation();
            } else {
                return "분석이 완료되었습니다.";
            }
                    
        } catch (Exception e) {
            log.error("Claude API 호출 실패: error={}", e.getMessage(), e);
            throw new ExternalApiException("AI 분석 요청에 실패했습니다.");
        }
    }
    
    @Override
    public String requestChatResponse(String prompt) {
        try {
            log.info("Claude API 채팅 요청: promptLength={}", prompt.length());
            
            // 실제 구현에서는 Claude API 호출
            // Mock 응답 반환
            return generateMockChatResponse(prompt);
                    
        } catch (Exception e) {
            log.error("Claude API 채팅 호출 실패: error={}", e.getMessage(), e);
            throw new ExternalApiException("AI 채팅 요청에 실패했습니다.");
        }
    }
    
    /**
     * Mock 건강 분석 응답을 생성합니다.
     * 
     * @return Mock 건강 분석 응답
     */
    private String generateMockHealthAnalysis() {
        return """
            1. 전반적으로 약간의 개선이 필요한 상태입니다.
            2. 혈압과 BMI 관리가 필요하며, 규칙적인 운동을 권장합니다.
            3. IT개발 직업 특성상 장시간 앉아있는 시간을 줄이고 스트레칭을 자주 해주세요.
            
            건강 점수: 75점
            """;
    }
    
    /**
     * Mock 미션 추천 응답을 생성합니다.
     * 
     * @return Mock 미션 추천 응답
     */
    private String generateMockMissionRecommendation() {
        return """
            IT개발자를 위한 맞춤형 건강 미션을 추천드립니다:
            
            1. 하루 8잔 물마시기 - 장시간 집중으로 인한 탈수 방지
            2. 점심시간 10분 산책 - 모니터 피로와 스트레스 해소
            3. 어깨 스트레칭 3세트 - 키보드 마우스 사용으로 인한 결림 완화
            4. 계단 이용하기 - 운동 부족 해소
            5. 금연/금주 도전 - 전반적 건강 증진
            """;
    }
    
    /**
     * Mock 채팅 응답을 생성합니다.
     * 
     * @param prompt 사용자 질문
     * @return Mock 채팅 응답
     */
    private String generateMockChatResponse(String prompt) {
        if (prompt.contains("운동")) {
            return "규칙적인 운동은 건강 관리의 핵심입니다. 일주일에 3-4회, 30분 정도의 유산소 운동을 권장합니다. 특히 IT 직군이시라면 목과 어깨 스트레칭도 함께 해주시면 좋습니다.";
        } else if (prompt.contains("식단")) {
            return "균형 잡힌 식단이 중요합니다. 단백질, 탄수화물, 지방을 적절히 섭취하고, 야식은 피하시는 것이 좋습니다. 특히 야근이 많으시다면 규칙적인 식사 시간을 지키려 노력해보세요.";
        } else if (prompt.contains("스트레스")) {
            return "스트레스 관리는 정신건강뿐만 아니라 신체 건강에도 매우 중요합니다. 충분한 휴식, 취미 활동, 명상 등을 통해 스트레스를 관리해보세요. 필요시 전문가의 도움을 받는 것도 좋습니다.";
        } else {
            return "건강과 관련된 궁금한 점이 있으시면 언제든 물어보세요. 더 구체적인 증상이나 문제가 있으시다면 의료진과 상담받으시는 것을 권장합니다.";
        }
    }
}
