// common/src/main/java/com/healthsync/common/exception/GlobalExceptionHandler.java
package com.healthsync.common.exception;

import com.healthsync.common.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 전역 예외 처리 핸들러입니다.
 *
 * @author healthsync-team
 * @version 1.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 비즈니스 예외 처리
     *
     * @param ex BusinessException
     * @return ErrorResponse
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
        log.warn("Business exception occurred: {}", ex.getMessage());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(ex.getErrorCode())
                .message(ex.getMessage())
                .build();
        return ResponseEntity.badRequest().body(errorResponse);
    }

    /**
     * 유효성 검증 예외 처리 (RequestBody)
     *
     * @param ex MethodArgumentNotValidException
     * @return ErrorResponse
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        log.warn("Validation exception occurred: {}", ex.getMessage());
        String message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("VALIDATION_ERROR")
                .message(message)
                .build();
        return ResponseEntity.badRequest().body(errorResponse);
    }

    /**
     * 일반적인 예외 처리
     *
     * @param ex Exception
     * @return ErrorResponse
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        log.error("Unexpected exception occurred", ex);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("INTERNAL_SERVER_ERROR")
                .message("서버 내부 오류가 발생했습니다.")
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}