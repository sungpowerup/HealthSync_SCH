package com.healthsync.goal.infrastructure.adapters;

import com.healthsync.common.exception.ExternalApiException;
import com.healthsync.goal.dto.CelebrationResponse;
import com.healthsync.goal.dto.Mission;
import com.healthsync.goal.infrastructure.ports.IntelligenceServicePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

/**
 * Intelligence Service와의 통신을 담당하는 어댑터 클래스입니다.
 * Clean Architecture의 Infrastructure 계층에 해당합니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class IntelligenceServiceAdapter implements IntelligenceServicePort {
    
    private final WebClient webClient = WebClient.builder().build();
    
    @Value("${services.intelligence-service.url}")
    private String intelligenceServiceUrl;
    
    @Override
    public CelebrationResponse requestCelebrationMessage(String userId, String missionId, int streakDays) {
        try {
            log.info("Intelligence Service 축하 메시지 요청: userId={}, missionId={}, streakDays={}", 
                    userId, missionId, streakDays);
            
            // 실제 구현에서는 Intelligence Service API 호출
            // Mock 데이터 반환
            return CelebrationResponse.builder()
                    .congratsMessage(String.format("🎉 %d일 연속 달성! 정말 훌륭합니다!", streakDays))
                    .achievementBadge("연속달성자")
                    .healthBenefit("꾸준한 미션 수행으로 건강이 개선되고 있습니다.")
                    .nextMilestone(String.format("다음 목표: %d일 연속 달성", streakDays + 5))
                    .build();
                    
        } catch (Exception e) {
            log.error("Intelligence Service 축하 메시지 요청 실패: userId={}, error={}", userId, e.getMessage(), e);
            throw new ExternalApiException("축하 메시지 생성에 실패했습니다.");
        }
    }
    
    @Override
    public List<Mission> requestNewMissionRecommendations(String userId, String resetReason) {
        try {
            log.info("Intelligence Service 새 미션 추천 요청: userId={}, reason={}", userId, resetReason);
            
            // 실제 구현에서는 Intelligence Service API 호출
            // Mock 데이터 반환
            return List.of(
                    Mission.builder()
                            .missionId("mission_6")
                            .title("스마트워치로 심박수 체크")
                            .description("하루 3번 심박수를 체크하여 건강 상태를 모니터링하세요.")
                            .category("모니터링")
                            .difficulty("초급")
                            .healthBenefit("심혈관 건강 상태 파악")
                            .occupationRelevance("IT 업무로 인한 스트레스 모니터링에 도움")
                            .estimatedTimeMinutes(5)
                            .build(),
                    Mission.builder()
                            .missionId("mission_7")
                            .title("명상 앱으로 5분 명상")
                            .description("업무 중 5분간 명상으로 마음의 평안을 찾으세요.")
                            .category("정신건강")
                            .difficulty("초급")
                            .healthBenefit("스트레스 감소, 집중력 향상")
                            .occupationRelevance("고강도 업무 환경에서 정신건강 관리")
                            .estimatedTimeMinutes(5)
                            .build(),
                    Mission.builder()
                            .missionId("mission_8")
                            .title("건강한 간식 섭취")
                            .description("견과류나 과일 등 건강한 간식으로 에너지를 보충하세요.")
                            .category("식습관")
                            .difficulty("초급")
                            .healthBenefit("영양 균형, 혈당 안정화")
                            .occupationRelevance("불규칙한 식사 시간 보완")
                            .estimatedTimeMinutes(10)
                            .build()
            );
                    
        } catch (Exception e) {
            log.error("Intelligence Service 미션 추천 요청 실패: userId={}, error={}", userId, e.getMessage(), e);
            throw new ExternalApiException("새로운 미션 추천에 실패했습니다.");
        }
    }
}
