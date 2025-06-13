package com.healthsync.intelligence.infrastructure.ports;

/**
 * Claude API와의 통신을 위한 포트 인터페이스입니다.
 * Clean Architecture의 Domain 계층에서 정의합니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
public interface ClaudeApiPort {
    
    /**
     * Claude API를 통해 분석을 요청합니다.
     * 
     * @param prompt 분석 프롬프트
     * @return AI 분석 결과
     */
    String requestAnalysis(String prompt);
    
    /**
     * Claude API를 통해 채팅 응답을 요청합니다.
     * 
     * @param prompt 채팅 프롬프트
     * @return AI 채팅 응답
     */
    String requestChatResponse(String prompt);
}
