package com.healthsync.motivator.infrastructure.ports;

/**
 * Claude API와의 통신을 위한 포트 인터페이스입니다.
 * Clean Architecture의 Domain 계층에서 정의합니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
public interface ClaudeApiPort {
    
    /**
     * Claude API를 호출합니다.
     * 
     * @param prompt 프롬프트
     * @return AI 응답
     */
    String callClaudeApi(String prompt);
}
