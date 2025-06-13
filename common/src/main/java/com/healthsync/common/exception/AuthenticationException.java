// common/src/main/java/com/healthsync/common/exception/AuthenticationException.java
package com.healthsync.common.exception;

import com.healthsync.common.constants.ErrorCode;

/**
 * 인증 실패 시 발생하는 예외입니다.
 *
 * @author healthsync-team
 * @version 1.0
 */
public class AuthenticationException extends BusinessException {

    /**
     * AuthenticationException 생성자
     *
     * @param message 에러 메시지
     */
    public AuthenticationException(String message) {
        super(ErrorCode.AUTHENTICATION_FAILED, message);
    }
}