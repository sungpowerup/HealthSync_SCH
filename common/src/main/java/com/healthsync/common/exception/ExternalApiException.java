package com.healthsync.common.exception;

/**
 * 외부 API 호출 실패 시 발생하는 예외 클래스입니다.
 *
 * @author healthsync-team
 * @version 1.0
 */
public class ExternalApiException extends BusinessException {

    public ExternalApiException(String message) {
        super("EXTERNAL_API_ERROR", message);
    }

    public ExternalApiException(String message, Throwable cause) {
        super("EXTERNAL_API_ERROR", message, cause);
    }
}