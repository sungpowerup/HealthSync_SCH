package com.healthsync.common.constants;

/**
 * 시스템에서 사용되는 에러 코드 상수 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
public class ErrorCode {
    
    // 인증 관련
    public static final String AUTHENTICATION_FAILED = "AUTH_001";
    public static final String INVALID_TOKEN = "AUTH_002";
    public static final String TOKEN_EXPIRED = "AUTH_003";
    
    // 사용자 관련
    public static final String USER_NOT_FOUND = "USER_001";
    public static final String USER_ALREADY_EXISTS = "USER_002";
    public static final String INVALID_USER_DATA = "USER_003";
    
    // 건강 데이터 관련
    public static final String HEALTH_DATA_NOT_FOUND = "HEALTH_001";
    public static final String HEALTH_SYNC_FAILED = "HEALTH_002";
    public static final String FILE_UPLOAD_FAILED = "HEALTH_003";
    
    // AI 관련
    public static final String AI_SERVICE_UNAVAILABLE = "AI_001";
    public static final String AI_ANALYSIS_FAILED = "AI_002";
    public static final String CLAUDE_API_ERROR = "AI_003";
    
    // 목표 관련
    public static final String GOAL_NOT_FOUND = "GOAL_001";
    public static final String MISSION_NOT_FOUND = "GOAL_002";
    public static final String INVALID_MISSION_STATUS = "GOAL_003";
    
    // 외부 서비스 관련
    public static final String EXTERNAL_SERVICE_ERROR = "EXT_001";
    public static final String GOOGLE_OAUTH_ERROR = "EXT_002";
}
