package com.healthsync.intelligence.domain.services;

import com.healthsync.intelligence.dto.*;
import com.healthsync.intelligence.infrastructure.ports.ClaudeApiPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

/**
 * AI 분석을 담당하는 도메인 서비스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AiAnalysisDomainService {
    
    private final ClaudeApiPort claudeApiPort;
    
    /**
     * AI 건강 진단을 생성합니다.
     * 
     * @param userProfile 사용자 프로필
     * @param healthData 건강 데이터
     * @return 건강 진단 결과
     */
    public HealthDiagnosisResponse generateHealthDiagnosis(UserProfile userProfile, HealthData healthData) {
        log.info("AI 건강 진단 생성: userId={}, occupation={}", userProfile.getUserId(), userProfile.getOccupation());
        
        // AI 프롬프트 구성
        String prompt = buildHealthDiagnosisPrompt(userProfile, healthData);
        
        // Claude API 호출
        String aiResponse = claudeApiPort.requestAnalysis(prompt);
        
        // 응답 파싱 및 구조화
        return parseHealthDiagnosisResponse(aiResponse, userProfile, healthData);
    }
    
    /**
     * AI 미션 추천을 생성합니다.
     * 
     * @param userProfile 사용자 프로필
     * @param healthData 건강 데이터
     * @return 미션 추천 결과
     */
    public MissionRecommendationResponse generateMissionRecommendations(UserProfile userProfile, HealthData healthData) {
        log.info("AI 미션 추천 생성: userId={}, occupation={}", userProfile.getUserId(), userProfile.getOccupation());
        
        // AI 프롬프트 구성
        String prompt = buildMissionRecommendationPrompt(userProfile, healthData);
        
        // Claude API 호출
        String aiResponse = claudeApiPort.requestAnalysis(prompt);
        
        // 응답 파싱 및 구조화
        return parseMissionRecommendationResponse(aiResponse, userProfile);
    }
    
    /**
     * 건강 진단 프롬프트를 구성합니다.
     * 
     * @param userProfile 사용자 프로필
     * @param healthData 건강 데이터
     * @return 프롬프트 문자열
     */
    private String buildHealthDiagnosisPrompt(UserProfile userProfile, HealthData healthData) {
        return String.format("""
            당신은 전문 의료 AI 어시스턴트입니다. 다음 정보를 바탕으로 건강검진 결과를 3줄로 요약해주세요.
            
            [사용자 정보]
            - 나이: %d세
            - 성별: %s
            - 직업군: %s
            
            [건강검진 결과]
            - BMI: %.1f
            - 혈압: %s
            - 콜레스테롤: %s
            - 혈당: %s
            
            다음 형식으로 응답해주세요:
            1. 전반적인 건강 상태 평가 (1줄)
            2. 주요 위험 요소 또는 개선점 (1줄)
            3. %s 직업군 고려한 맞춤 조언 (1줄)
            
            건강 점수도 0-100점으로 매겨주세요.
            """, 
            userProfile.getAge(), userProfile.getGender(), userProfile.getOccupation(),
            healthData.getBmi(), healthData.getBloodPressure(), 
            healthData.getCholesterol(), healthData.getBloodSugar(),
            userProfile.getOccupation());
    }
    
    /**
     * 미션 추천 프롬프트를 구성합니다.
     * 
     * @param userProfile 사용자 프로필
     * @param healthData 건강 데이터
     * @return 프롬프트 문자열
     */
    private String buildMissionRecommendationPrompt(UserProfile userProfile, HealthData healthData) {
        return String.format("""
            당신은 건강 관리 전문가입니다. 다음 정보를 바탕으로 5개의 맞춤형 건강 미션을 추천해주세요.
            
            [사용자 정보]
            - 나이: %d세, 성별: %s
            - 직업군: %s
            - BMI: %.1f, 혈압: %s
            
            [추천 조건]
            - 직업군 특성을 고려한 실행 가능한 미션
            - 각 미션은 15-30분 내 완료 가능
            - 초급, 중급, 고급 난이도 혼합
            - 운동, 식습관, 생활습관 카테고리 포함
            
            각 미션마다 다음 정보를 포함해주세요:
            1. 미션 제목
            2. 상세 설명
            3. 카테고리 (운동/식습관/생활습관)
            4. 난이도 (초급/중급/고급)
            5. 예상 소요시간(분)
            6. 건강 효과
            7. %s 직업군 관련성
            """, 
            userProfile.getAge(), userProfile.getGender(), userProfile.getOccupation(),
            healthData.getBmi(), healthData.getBloodPressure(), userProfile.getOccupation());
    }
    
    /**
     * AI 건강 진단 응답을 파싱합니다.
     * 
     * @param aiResponse AI 응답
     * @param userProfile 사용자 프로필
     * @param healthData 건강 데이터
     * @return 구조화된 건강 진단 결과
     */
    private HealthDiagnosisResponse parseHealthDiagnosisResponse(String aiResponse, UserProfile userProfile, HealthData healthData) {
        // Mock 파싱 로직 (실제 구현에서는 AI 응답을 파싱)
        List<String> summaryLines = List.of(
                "전반적으로 약간의 개선이 필요한 상태입니다.",
                "혈압과 BMI 관리가 필요하며, 규칙적인 운동을 권장합니다.",
                userProfile.getOccupation() + " 특성상 장시간 앉아있는 시간을 줄이고 스트레칭을 자주 해주세요."
        );
        
        return HealthDiagnosisResponse.builder()
                .threeSentenceSummary(summaryLines)
                .healthScore(calculateHealthScore(healthData))
                .riskLevel(determineRiskLevel(healthData))
                .occupationConsiderations(getOccupationConsiderations(userProfile.getOccupation()))
                .analysisTimestamp(LocalDateTime.now().toString())
                .confidenceScore(0.85)
                .build();
    }
    
    /**
     * AI 미션 추천 응답을 파싱합니다.
     * 
     * @param aiResponse AI 응답
     * @param userProfile 사용자 프로필
     * @return 구조화된 미션 추천 결과
     */
    private MissionRecommendationResponse parseMissionRecommendationResponse(String aiResponse, UserProfile userProfile) {
        // Mock 미션 생성 (실제 구현에서는 AI 응답을 파싱)
        List<Mission> missions = IntStream.range(1, 6)
                .mapToObj(i -> Mission.builder()
                        .missionId("mission_" + i)
                        .title(getMockMissionTitle(i))
                        .description(getMockMissionDescription(i))
                        .category(getMockMissionCategory(i))
                        .difficulty(getMockMissionDifficulty(i))
                        .healthBenefit(getMockHealthBenefit(i))
                        .occupationRelevance(getOccupationRelevance(userProfile.getOccupation(), i))
                        .estimatedTimeMinutes(15 + (i * 5))
                        .build())
                .toList();
        
        return MissionRecommendationResponse.builder()
                .missions(missions)
                .recommendationReason(userProfile.getOccupation() + " 직업군의 특성과 건강 상태를 고려하여 맞춤형 미션을 추천드립니다.")
                .totalRecommended(missions.size())
                .build();
    }
    
    /**
     * 건강 점수를 계산합니다.
     * 
     * @param healthData 건강 데이터
     * @return 건강 점수 (0-100)
     */
    private int calculateHealthScore(HealthData healthData) {
        int score = 100;
        
        // BMI 평가
        if (healthData.getBmi() >= 25) score -= 15;
        else if (healthData.getBmi() >= 23) score -= 5;
        
        // 혈압 평가 (간단한 예시)
        if (healthData.getBloodPressure().contains("140") || healthData.getBloodPressure().contains("90")) {
            score -= 20;
        }
        
        return Math.max(score, 0);
    }
    
    /**
     * 위험 수준을 결정합니다.
     * 
     * @param healthData 건강 데이터
     * @return 위험 수준
     */
    private String determineRiskLevel(HealthData healthData) {
        int score = calculateHealthScore(healthData);
        if (score >= 80) return "낮음";
        else if (score >= 60) return "보통";
        else return "높음";
    }
    
    /**
     * 직업군별 고려사항을 반환합니다.
     * 
     * @param occupation 직업군
     * @return 고려사항
     */
    private String getOccupationConsiderations(String occupation) {
        return switch (occupation.toLowerCase()) {
            case "it", "it개발" -> "장시간 앉아서 작업하므로 목과 어깨, 허리 관리가 중요합니다.";
            case "마케팅" -> "스트레스 관리와 불규칙한 식사 패턴 개선이 필요합니다.";
            case "금융" -> "정신적 스트레스가 높으므로 휴식과 운동의 균형이 중요합니다.";
            case "선생님" -> "목소리 관리와 서서 일하는 시간이 많아 하체 강화가 필요합니다.";
            case "고객상담" -> "정서적 스트레스 관리와 규칙적인 휴식이 중요합니다.";
            default -> "규칙적인 생활 패턴과 꾸준한 운동이 건강 관리의 핵심입니다.";
        };
    }
    
    // Mock 데이터 생성 메서드들
    private String getMockMissionTitle(int index) {
        String[] titles = {
                "하루 8잔 물마시기",
                "점심시간 10분 산책",
                "어깨 스트레칭 3세트",
                "계단 이용하기",
                "금연/금주 도전"
        };
        return titles[index - 1];
    }
    
    private String getMockMissionDescription(int index) {
        String[] descriptions = {
                "하루 종일 충분한 수분 섭취로 신진대사를 활발하게 유지하세요.",
                "점심식사 후 가벼운 산책으로 소화를 돕고 스트레스를 해소하세요.",
                "장시간 앉아있는 자세로 인한 어깨 결림을 스트레칭으로 완화하세요.",
                "엘리베이터 대신 계단을 이용하여 하체 근력을 강화하세요.",
                "건강한 생활습관을 위해 금연과 절주에 도전해보세요."
        };
        return descriptions[index - 1];
    }
    
    private String getMockMissionCategory(int index) {
        String[] categories = {"생활습관", "운동", "운동", "운동", "생활습관"};
        return categories[index - 1];
    }
    
    private String getMockMissionDifficulty(int index) {
        String[] difficulties = {"초급", "초급", "중급", "중급", "고급"};
        return difficulties[index - 1];
    }
    
    private String getMockHealthBenefit(int index) {
        String[] benefits = {
                "신진대사 활성화, 피부 건강 개선",
                "소화 촉진, 스트레스 완화",
                "어깨 결림 완화, 혈액순환 개선",
                "하체 근력 강화, 심폐기능 향상",
                "폐 건강 개선, 전반적 건강 증진"
        };
        return benefits[index - 1];
    }
    
    private String getOccupationRelevance(String occupation, int index) {
        if (occupation.toLowerCase().contains("it")) {
            String[] relevance = {
                    "장시간 앉아있는 개발자에게 필수적인 수분 보충",
                    "모니터 집중으로 인한 눈의 피로와 스트레스 해소",
                    "키보드와 마우스 사용으로 인한 어깨 결림 완화",
                    "엘리베이터 의존도가 높은 사무직에게 운동 기회 제공",
                    "야근과 스트레스가 많은 개발자의 건강한 생활습관 형성"
            };
            return relevance[index - 1];
        }
        return "해당 직업군의 특성을 고려한 맞춤형 건강 관리 방법입니다.";
    }
}
