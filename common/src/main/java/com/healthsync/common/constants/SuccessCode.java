package com.healthsync.common.constants;

/**
 * 성공 응답 메시지 상수 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
public class SuccessCode {
    
    // 인증 관련
    public static final String LOGIN_SUCCESS = "로그인이 완료되었습니다.";
    public static final String LOGOUT_SUCCESS = "로그아웃이 완료되었습니다.";
    
    // 사용자 관련
    public static final String USER_REGISTRATION_SUCCESS = "회원가입이 완료되었습니다.";
    public static final String PROFILE_UPDATE_SUCCESS = "프로필이 업데이트되었습니다.";
    
    // 건강 데이터 관련
    public static final String HEALTH_SYNC_SUCCESS = "건강검진 데이터 연동이 완료되었습니다.";
    public static final String FILE_UPLOAD_SUCCESS = "파일 업로드가 완료되었습니다.";
    
    // 목표 관련
    public static final String MISSION_COMPLETE_SUCCESS = "미션이 완료되었습니다.";
    public static final String GOAL_SETUP_SUCCESS = "목표 설정이 완료되었습니다.";
}
