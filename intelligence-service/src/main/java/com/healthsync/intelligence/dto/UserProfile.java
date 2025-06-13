package com.healthsync.intelligence.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 사용자 프로필 DTO 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "사용자 프로필")
public class UserProfile {
    
    @Schema(description = "사용자 ID")
    private String userId;
    
    @Schema(description = "이름")
    private String name;
    
    @Schema(description = "나이")
    private int age;
    
    @Schema(description = "성별")
    private String gender;
    
    @Schema(description = "직업군")
    private String occupation;
}
