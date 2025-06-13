package com.healthsync.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 로그인 응답 DTO 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "로그인 응답")
public class LoginResponse {
    
    @Schema(description = "액세스 토큰")
    private String accessToken;
    
    @Schema(description = "리프레시 토큰")
    private String refreshToken;
    
    @Schema(description = "신규 회원 여부")
    private boolean isNewUser;
    
    @Schema(description = "사용자 ID")
    private String userId;
    
    @Schema(description = "토큰 만료 시간(초)")
    private int expiresIn;
}
