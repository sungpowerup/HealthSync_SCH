package com.healthsync.health.domain.services;

import com.healthsync.health.dto.CheckupRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 건강 데이터 분석을 담당하는 도메인 서비스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HealthAnalysisDomainService {
    
    /**
     * 건강검진 기록으로부터 차트 데이터를 생성합니다.
     * 
     * @param checkupRecords 건강검진 기록 목록
     * @return 차트 데이터
     */
    public Object generateChartData(List<CheckupRecord> checkupRecords) {
        log.info("차트 데이터 생성: recordCount={}", checkupRecords.size());
        
        Map<String, Object> chartData = new HashMap<>();
        
        // BMI 추이 데이터
        List<Map<String, Object>> bmiTrend = checkupRecords.stream()
                .map(record -> {
                    Map<String, Object> data = new HashMap<>();
                    data.put("year", record.getYear());
                    data.put("bmi", record.getBmi());
                    return data;
                })
                .toList();
        
        chartData.put("bmiTrend", bmiTrend);
        chartData.put("chartType", "line");
        chartData.put("title", "BMI 추이");
        
        return chartData;
    }
    
    /**
     * 건강 점수를 계산합니다.
     * 
     * @param checkupRecord 건강검진 기록
     * @return 건강 점수 (0-100)
     */
    public int calculateHealthScore(CheckupRecord checkupRecord) {
        int score = 100;
        
        // BMI 점수 계산
        if (checkupRecord.getBmi() < 18.5 || checkupRecord.getBmi() >= 25) {
            score -= 15;
        } else if (checkupRecord.getBmi() >= 23) {
            score -= 5;
        }
        
        // 혈압 점수 계산 (간단한 예시)
        String[] bp = checkupRecord.getBloodPressure().split("/");
        if (bp.length == 2) {
            int systolic = Integer.parseInt(bp[0]);
            int diastolic = Integer.parseInt(bp[1]);
            
            if (systolic >= 140 || diastolic >= 90) {
                score -= 20;
            } else if (systolic >= 130 || diastolic >= 85) {
                score -= 10;
            }
        }
        
        return Math.max(score, 0);
    }
}
