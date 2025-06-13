package com.healthsync.intelligence.domain.services;

import com.healthsync.common.exception.ValidationException;
import com.healthsync.intelligence.dto.ChatRequest;
import com.healthsync.intelligence.dto.ChatResponse;
import com.healthsync.intelligence.dto.ChatMessage;
import com.healthsync.intelligence.infrastructure.ports.ClaudeApiPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 채팅 관련 비즈니스 로직을 처리하는 도메인 서비스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ChatDomainService {
    
    private final ClaudeApiPort claudeApiPort;
    
    /**
     * 채팅 입력을 검증합니다.
     * 
     * @param message 채팅 메시지
     */
    public void validateChatInput(String message) {
        if (message == null || message.trim().isEmpty()) {
            throw new ValidationException("메시지가 비어있습니다.");
        }
        
        if (message.length() > 1000) {
            throw new ValidationException("메시지는 1000자 이내로 입력해주세요.");
        }
        
        // 부적절한 내용 필터링 (간단한 예시)
        if (containsInappropriateContent(message)) {
            throw new ValidationException("부적절한 내용이 포함되어 있습니다.");
        }
    }
    
    /**
     * AI 채팅 응답을 생성합니다.
     * 
     * @param request 채팅 요청
     * @param chatHistory 채팅 이력
     * @return AI 응답
     */
    public ChatResponse generateChatResponse(ChatRequest request, List<ChatMessage> chatHistory) {
        log.info("AI 채팅 응답 생성: sessionId={}", request.getSessionId());
        
        // 채팅 프롬프트 구성
        String prompt = buildChatPrompt(request, chatHistory);
        
        // Claude API 호출
        String aiResponse = claudeApiPort.requestChatResponse(prompt);
        
        // 추천 질문 생성
        List<String> suggestedQuestions = generateSuggestedQuestions(request.getMessage());
        
        return ChatResponse.builder()
                .response(aiResponse)
                .sessionId(request.getSessionId())
                .timestamp(LocalDateTime.now().toString())
                .suggestedQuestions(suggestedQuestions)
                .responseType("consultation")
                .build();
    }
    
    /**
     * 채팅 프롬프트를 구성합니다.
     * 
     * @param request 채팅 요청
     * @param chatHistory 채팅 이력
     * @return 프롬프트 문자열
     */
    private String buildChatPrompt(ChatRequest request, List<ChatMessage> chatHistory) {
        StringBuilder prompt = new StringBuilder();
        
        prompt.append("당신은 전문적이고 친근한 건강 관리 AI 어시스턴트입니다.\n");
        prompt.append("사용자의 건강 관련 질문에 정확하고 도움이 되는 답변을 제공해주세요.\n\n");
        
        // 채팅 이력 추가
        if (!chatHistory.isEmpty()) {
            prompt.append("[이전 대화 내용]\n");
            for (ChatMessage message : chatHistory) {
                prompt.append(String.format("%s: %s\n", message.getRole(), message.getContent()));
            }
            prompt.append("\n");
        }
        
        // 현재 질문 추가
        prompt.append(String.format("[사용자 질문]\n%s\n\n", request.getMessage()));
        
        prompt.append("[응답 가이드라인]\n");
        prompt.append("- 의학적 조언이 아닌 일반적인 건강 정보 제공\n");
        prompt.append("- 구체적인 증상은 의료진 상담 권유\n");
        prompt.append("- 친근하고 이해하기 쉬운 언어 사용\n");
        prompt.append("- 200자 이내의 간결한 답변\n");
        
        return prompt.toString();
    }
    
    /**
     * 추천 질문을 생성합니다.
     * 
     * @param userMessage 사용자 메시지
     * @return 추천 질문 목록
     */
    private List<String> generateSuggestedQuestions(String userMessage) {
        // 키워드 기반 추천 질문 생성 (간단한 예시)
        if (userMessage.contains("운동") || userMessage.contains("헬스")) {
            return List.of(
                    "어떤 운동이 제일 효과적인가요?",
                    "운동 시간은 얼마나 해야 하나요?",
                    "집에서 할 수 있는 운동이 있나요?"
            );
        } else if (userMessage.contains("식단") || userMessage.contains("음식")) {
            return List.of(
                    "건강한 식단 구성 방법이 궁금해요",
                    "체중 관리에 좋은 음식은 뭔가요?",
                    "피해야 할 음식이 있나요?"
            );
        } else {
            return List.of(
                    "건강 점수를 올리려면 어떻게 해야 하나요?",
                    "스트레스 관리 방법이 궁금해요",
                    "수면의 질을 개선하고 싶어요"
            );
        }
    }
    
    /**
     * 부적절한 내용 포함 여부를 확인합니다.
     * 
     * @param message 메시지
     * @return 부적절한 내용 포함 여부
     */
    private boolean containsInappropriateContent(String message) {
        // 간단한 필터링 로직 (실제 구현에서는 더 정교한 필터링 필요)
        String[] inappropriateWords = {"욕설", "비방", "광고"};
        String lowerMessage = message.toLowerCase();
        
        for (String word : inappropriateWords) {
            if (lowerMessage.contains(word)) {
                return true;
            }
        }
        
        return false;
    }
}
