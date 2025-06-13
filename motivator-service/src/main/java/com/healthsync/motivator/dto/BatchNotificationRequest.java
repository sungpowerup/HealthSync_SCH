package com.healthsync.motivator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * 배치 알림 요청 DTO 클래스입니다.
 *
 * @author healthsync-team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "배치 알림 요청")
public class BatchNotificationRequest {

    @NotBlank(message = "트리거 시간은 필수입니다.")
    @Schema(description = "트리거 시간")
    private String triggerTime;

    @NotNull(message = "대상 사용자 목록은 필수입니다.")
    @Schema(description = "대상 사용자 ID 목록")
    private List<String> targetUsers;

    @NotBlank(message = "알림 유형은 필수입니다.")
    @Schema(description = "알림 유형")
    private String notificationType;
}