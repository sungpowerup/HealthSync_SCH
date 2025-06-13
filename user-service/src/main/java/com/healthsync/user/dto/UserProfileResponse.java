package com.healthsync.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 사용자 프로필 응답 DTO 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "사용자 프로필 응답")
public class UserProfileResponse {
    
    @Schema(description = "사용자 ID")
    private String userId;
    
    @Schema(description = "이름")
    private String name;
    
    @Schema(description = "나이")
    private int age;
    
    @Schema(description = "직업군")
    private String occupation;
    
    @Schema(description = "가입일시")
    private String registeredAt;
    
    @Schema(description = "최근 로그인 일시")
    private String lastLoginAt;
}
