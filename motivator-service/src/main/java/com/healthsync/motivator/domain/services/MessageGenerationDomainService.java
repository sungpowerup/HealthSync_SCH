package com.healthsync.motivator.domain.services;

import com.healthsync.motivator.dto.ProgressAnalysis;
import com.healthsync.motivator.infrastructure.ports.ClaudeApiPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 메시지 생성을 담당하는 도메인 서비스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MessageGenerationDomainService {
    
    /**
     * 독려 메시지를 생성합니다.
     * 
     * @param progressAnalysis 진행 분석
     * @param claudeApiPort Claude API 포트
     * @return 독려 메시지
     */
    public String generateEncouragementMessage(ProgressAnalysis progressAnalysis, ClaudeApiPort claudeApiPort) {
        log.info("독려 메시지 생성: userId={}, motivationType={}", 
                progressAnalysis.getUserId(), progressAnalysis.getMotivationType());
        
        try {
            // AI 프롬프트 준비
            String prompt = prepareEncouragementPrompt(progressAnalysis);
            
            // Claude API 호출
            String aiResponse = claudeApiPort.callClaudeApi(prompt);
            
            // 응답 포맷팅
            return formatEncouragementResponse(aiResponse);
            
        } catch (Exception e) {
            log.warn("AI 메시지 생성 실패, 기본 메시지 사용: userId={}, error={}", 
                    progressAnalysis.getUserId(), e.getMessage());
            return useFallbackMessage(progressAnalysis);
        }
    }
    
    /**
     * 독려 프롬프트를 준비합니다.
     * 
     * @param analysis 진행 분석
     * @return 프롬프트
     */
    private String prepareEncouragementPrompt(ProgressAnalysis analysis) {
        return String.format("""
            당신은 친근하고 따뜻한 건강 코치입니다. 다음 정보를 바탕으로 개인화된 독려 메시지를 생성해주세요.
            
            [사용자 진행 상황]
            - 완료율: %.1f%% (%d/%d 미션 완료)
            - 연속 달성 일수: %d일
            - 동기부여 유형: %s
            - 긴급도: %s
            - 주간 완료율: %.1f%%
            
            [실패한 미션]
            %s
            
            [요구사항]
            - 100자 이내의 간결한 메시지
            - %s 스타일의 동기부여
            - 구체적인 행동 제안 포함
            - 긍정적이고 격려하는 톤
            - 이모지 사용하여 친근감 표현
            """,
            analysis.getCompletionRate() * 100,
            analysis.getCompletedMissionsCount(),
            analysis.getTotalMissionsCount(),
            analysis.getStreakDays(),
            analysis.getMotivationType(),
            analysis.getUrgencyLevel(),
            analysis.getWeeklyCompletionRate() * 100,
            analysis.getFailurePoints().isEmpty() ? "없음" : String.join(", ", analysis.getFailurePoints()),
            getMotivationStyleDescription(analysis.getMotivationType())
        );
    }
    
    /**
     * AI 응답을 포맷팅합니다.
     * 
     * @param aiResponse AI 응답
     * @return 포맷팅된 메시지
     */
    private String formatEncouragementResponse(String aiResponse) {
        // AI 응답에서 불필요한 부분 제거 및 길이 제한
        String cleaned = aiResponse.trim()
                .replaceAll("\\n+", " ")
                .replaceAll("\\s+", " ");
        
        if (cleaned.length() > 100) {
            cleaned = cleaned.substring(0, 97) + "...";
        }
        
        return cleaned;
    }
    
    /**
     * 기본 메시지를 사용합니다.
     * 
     * @param analysis 진행 분석
     * @return 기본 독려 메시지
     */
    private String useFallbackMessage(ProgressAnalysis analysis) {
        return switch (analysis.getMotivationType()) {
            case ACHIEVEMENT -> String.format("🎯 현재 %.0f%% 달성! 목표까지 조금만 더 화이팅!", 
                    analysis.getCompletionRate() * 100);
            case HABIT_FORMATION -> String.format("💪 %d일 연속 도전 중! 습관 만들기까지 파이팅!", 
                    analysis.getStreakDays());
            case SOCIAL -> "👥 함께라면 더 멀리 갈 수 있어요! 오늘도 건강한 하루 만들어봐요!";
            case HEALTH_BENEFIT -> "🌟 건강한 변화는 작은 실천에서 시작됩니다. 오늘 한 걸음 더!";
        };
    }
    
    /**
     * 동기부여 스타일 설명을 반환합니다.
     * 
     * @param motivationType 동기부여 유형
     * @return 스타일 설명
     */
    private String getMotivationStyleDescription(com.healthsync.motivator.enums.MotivationType motivationType) {
        return switch (motivationType) {
            case ACHIEVEMENT -> "성취감 중심";
            case HABIT_FORMATION -> "습관 형성 중심";
            case SOCIAL -> "사회적 동기 중심";
            case HEALTH_BENEFIT -> "건강 효과 중심";
        };
    }
}
