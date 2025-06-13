// common/src/main/java/com/healthsync/common/dto/ErrorResponse.java
package com.healthsync.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * API 에러 응답 DTO 클래스입니다.
 *
 * @author healthsync-team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private String code;
    private String message;

    @Builder.Default
    private String timestamp = LocalDateTime.now().toString();
}