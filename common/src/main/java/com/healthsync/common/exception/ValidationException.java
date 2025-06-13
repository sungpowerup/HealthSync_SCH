package com.healthsync.common.exception;

/**
 * 유효성 검증 실패 시 발생하는 예외 클래스입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
public class ValidationException extends BusinessException {
    
    public ValidationException(String message) {
        super("VALIDATION_ERROR", message);
    }
}
