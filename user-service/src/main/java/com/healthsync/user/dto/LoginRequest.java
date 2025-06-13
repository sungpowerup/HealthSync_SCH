package com.healthsync.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;

/**
 * 로그인 요청 DTO 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "로그인 요청")
public class LoginRequest {
    
    @NotBlank(message = "인증 코드는 필수입니다.")
    @Schema(description = "Google OAuth 인증 코드", example = "4/0AX4XfWjM...")
    private String code;
}
