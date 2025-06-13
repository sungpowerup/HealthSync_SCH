// common/src/main/java/com/healthsync/common/dto/ApiResponse.java
package com.healthsync.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * 공통 API 응답 DTO 클래스입니다.
 *
 * @author healthsync-team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private int status;
    private String message;
    private T data;

    @Builder.Default
    private String timestamp = LocalDateTime.now().toString();

    private String traceId;

    /**
     * 성공 응답을 생성합니다.
     *
     * @param data 응답 데이터
     * @return ApiResponse 인스턴스
     */
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .status(200)
                .message("SUCCESS")
                .data(data)
                .timestamp(LocalDateTime.now().toString())
                .build();
    }

    /**
     * 성공 응답을 생성합니다.
     *
     * @param message 성공 메시지
     * @param data 응답 데이터
     * @return ApiResponse 인스턴스
     */
    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .status(200)
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now().toString())
                .build();
    }

    /**
     * 에러 응답을 생성합니다.
     *
     * @param message 에러 메시지
     * @return ApiResponse 인스턴스
     */
    public static <T> ApiResponse<T> error(String message) {
        return ApiResponse.<T>builder()
                .status(500)
                .message(message)
                .data(null)
                .timestamp(LocalDateTime.now().toString())
                .build();
    }

    /**
     * 성공 여부를 반환합니다.
     *
     * @return 성공 여부
     */
    public boolean isSuccess() {
        return status >= 200 && status < 300;
    }
}