// common/src/main/java/com/healthsync/common/exception/UserNotFoundException.java
package com.healthsync.common.exception;

import com.healthsync.common.constants.ErrorCode;

/**
 * 사용자를 찾을 수 없을 때 발생하는 예외입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
public class UserNotFoundException extends BusinessException {

    /**
     * UserNotFoundException 생성자
     *
     * @param userId 사용자 ID
     */
    public UserNotFoundException(String userId) {
        super(ErrorCode.USER_NOT_FOUND, "사용자를 찾을 수 없습니다: " + userId);
    }
}