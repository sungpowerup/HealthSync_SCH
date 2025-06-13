// common/src/main/java/com/healthsync/common/exception/BusinessException.java
package com.healthsync.common.exception;

import lombok.Getter;

/**
 * 비즈니스 로직에서 발생하는 예외를 처리하는 클래스입니다.
 *
 * @author healthsync-team
 * @version 1.0
 */
@Getter
public class BusinessException extends RuntimeException {

    private final String errorCode;

    public BusinessException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public BusinessException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}