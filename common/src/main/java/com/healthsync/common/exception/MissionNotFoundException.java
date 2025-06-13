// common/src/main/java/com/healthsync/common/exception/MissionNotFoundException.java
package com.healthsync.common.exception;

import com.healthsync.common.constants.ErrorCode;

/**
 * 미션을 찾을 수 없을 때 발생하는 예외입니다.
 * 
 * @author healthsync-team
 * @version 1.0
 */
public class MissionNotFoundException extends BusinessException {

    /**
     * MissionNotFoundException 생성자
     *
     * @param missionId 미션 ID
     */
    public MissionNotFoundException(String missionId) {
        super(ErrorCode.MISSION_NOT_FOUND, "미션을 찾을 수 없습니다: " + missionId);
    }
}