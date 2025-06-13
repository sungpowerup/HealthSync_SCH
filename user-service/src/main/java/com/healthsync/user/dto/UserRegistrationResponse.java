package com.healthsync.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 회원가입 응답 DTO 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "회원가입 응답")
public class UserRegistrationResponse {
    
    @Schema(description = "사용자 ID")
    private String userId;
    
    @Schema(description = "응답 메시지")
    private String message;
    
    @Schema(description = "처리 상태")
    private String status;
}
