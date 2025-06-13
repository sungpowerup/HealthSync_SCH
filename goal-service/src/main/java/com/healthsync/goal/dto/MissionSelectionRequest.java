package com.healthsync.goal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;

/**
 * 미션 선택 요청 DTO 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "미션 선택 요청")
public class MissionSelectionRequest {
    
    @NotBlank(message = "사용자 ID는 필수입니다.")
    @Schema(description = "사용자 ID")
    private String userId;
    
    @NotEmpty(message = "선택된 미션 ID 목록은 필수입니다.")
    @Size(min = 1, max = 5, message = "1개에서 5개까지 미션을 선택할 수 있습니다.")
    @Schema(description = "선택된 미션 ID 목록")
    private List<String> selectedMissionIds;
}
