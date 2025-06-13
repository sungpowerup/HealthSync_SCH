package com.healthsync.motivator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 독려 메시지 요청 DTO 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "독려 메시지 요청")
public class EncouragementRequest {
    
    @NotBlank(message = "사용자 ID는 필수입니다.")
    @Schema(description = "사용자 ID")
    private String userId;
    
    @NotEmpty(message = "미션 상태 정보는 필수입니다.")
    @Valid
    @Schema(description = "미션 상태 목록")
    private List<MissionStatus> missionsStatus;
}
