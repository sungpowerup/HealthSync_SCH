package com.healthsync.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * 회원가입 요청 DTO 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "회원가입 요청")
public class UserRegistrationRequest {
    
    @NotBlank(message = "사용자 ID는 필수입니다.")
    @Schema(description = "사용자 ID")
    private String userId;
    
    @NotBlank(message = "이름은 필수입니다.")
    @Schema(description = "이름", example = "홍길동")
    private String name;
    
    @NotBlank(message = "생년월일은 필수입니다.")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "생년월일은 yyyy-MM-dd 형식이어야 합니다.")
    @Schema(description = "생년월일", example = "1990-01-01")
    private String birthDate;
    
    @NotBlank(message = "직업군은 필수입니다.")
    @Schema(description = "직업군", example = "it")
    private String occupation;
}
